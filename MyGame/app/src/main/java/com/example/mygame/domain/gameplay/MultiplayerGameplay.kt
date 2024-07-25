package com.example.mygame.domain.gameplay

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Score
import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.view.BonusView
import com.example.mygame.domain.drawable.view.ObjectView
import com.example.mygame.domain.logic.SensorHandler
import com.example.mygame.multiplayer.FireMessage
import com.example.mygame.multiplayer.InitMessage
import com.example.mygame.multiplayer.JSONToKotlin
import com.example.mygame.multiplayer.MoveMessage
import com.example.mygame.multiplayer.Offset
import com.example.mygame.multiplayer.PlayerIdFromServer
import com.example.mygame.multiplayer.ReadyMessage
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class MultiplayerGameplay(
    resources: Resources,
    private val screen: Screen,
    private val scope: CoroutineScope
) : IGameplay, SensorHandler.SensorCallback {

    private val gson = Gson()

    private val client: WebSocketClient
    private val serverUri = URI("ws://10.250.104.162:8080")

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState

    override val score = Score()

    private val objectsViews = mutableListOf<ObjectView>()

    private val offset = Offset(screen)
    private val parserJSONToKotlin = JSONToKotlin(resources, gson, offset, objectsViews)

    private val _scoreObservable = MutableLiveData<Int>()
    override val scoreObservable: LiveData<Int> = _scoreObservable

    init {
        client = object : WebSocketClient(serverUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                sendInitMessage()
                Log.d("WebSocket", "Connection opened")
            }

            override fun onMessage(message: String?) {
                message?.let {
                    try {
                        if (parserJSONToKotlin.id == null) {
                            val initId = gson.fromJson(message, PlayerIdFromServer::class.java)
                            if (initId.id != 0) {
                                parserJSONToKotlin.id = initId.id
                                return
                            }
                        }

                        handleServerData(message)
                    } catch (e: JsonSyntaxException) {
                        Log.e("WebSocket", "Failed to parse server message", e)
                    }
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d("WebSocket", "Closed with exit code $code additional info: $reason")
            }

            override fun onError(ex: Exception?) {
                Log.e("WebSocket", "Error occurred:", ex)
            }
        }
        client.connect()
    }

    override fun onViewCreated() {
        var elapsedTime: Float
        var startTime = System.currentTimeMillis()

        scope.launch {
            while (true) {

                val systemTime = System.currentTimeMillis()
                elapsedTime = (systemTime - startTime) / 1000f

                if (elapsedTime < GameConstants.MAX_FRAME_TIME) {
                    delay(1)
                    continue
                }

                _gameState.postValue(GameState(Type.GAME, objectsViews, emptyList()))



                startTime = systemTime
            }
        }
    }

    override fun sendReadyMessage() {
        val message = gson.toJson(ReadyMessage(1))
        if (client.isOpen) {
            client.send(message)
        }
    }

    private fun sendInitMessage() {
        val message = gson.toJson(InitMessage(screen.width.toInt(), screen.height.toInt()))
        if (client.isOpen) {
            client.send(message)
        }
    }

    private fun sendMoveMessage(dx: Float) {
        val message = gson.toJson(MoveMessage(dx))
        if (client.isOpen) {
            client.send(message)
        }
    }

    private fun sendFireMessage() {
        val message = gson.toJson(FireMessage(1))
        if (client.isOpen) {
            client.send(message)
        }
    }

    private fun handleServerData(message: String) {
        parserJSONToKotlin.setGameState(message)
    }

    override fun onShot(startX: Float) {
        sendFireMessage()
    }

    override fun onSensorDataChanged(deltaX: Float) {
        sendMoveMessage(deltaX)
    }

    override fun onDestroy() {
        client.close()
    }
}