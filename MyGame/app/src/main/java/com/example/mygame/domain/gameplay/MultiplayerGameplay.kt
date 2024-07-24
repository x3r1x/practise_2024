package com.example.mygame.domain.gameplay

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.domain.Score
import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.view.ObjectView
import com.example.mygame.domain.logic.SensorHandler
import com.example.mygame.multiplayer.Camera
import com.example.mygame.multiplayer.FireMessage
import com.example.mygame.multiplayer.InitMessage
import com.example.mygame.multiplayer.JSONToKotlin
import com.example.mygame.multiplayer.MoveMessage
import com.example.mygame.multiplayer.ReadyMessage
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class MultiplayerGameplay(
    resources: Resources,
    private val screen: Screen
) : IGameplay, SensorHandler.SensorCallback {
    private val uiScope = CoroutineScope(Dispatchers.Main)

    private var isNewData = false

    private val gson = Gson()

    private var pingSentTime: Long = 0

    private val client: WebSocketClient
    private val serverUri = URI("ws://10.10.29.46:8080")

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState

    override val score = Score()

    //private val objects = mutableListOf<IGameObjectJSON>()
    private val objectsViews = mutableListOf<ObjectView>()

    private val camera = Camera(screen)
    private val parserJSONToKotlin = JSONToKotlin(resources, gson, camera, objectsViews)

    private val _scoreObservable = MutableLiveData<Int>()
    override val scoreObservable: LiveData<Int> = _scoreObservable

    init {
        client = object : WebSocketClient(serverUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                sendInitMessage()
                sendReadyMessage()
                Log.d("WebSocket", "Connection opened")
            }

            override fun onMessage(message: String?) {
                message?.let {
                    try {
//                        val serverResponse = gson.fromJson(message, ServerResponse::class.java)
//                        if (serverResponse.type == "pong") {
//                            val currentTime = System.currentTimeMillis()
//                            val ping = currentTime - pingSentTime
//                            Log.d("WebSocket", "Ping: $ping ms")
//                        } else {
//                        }
                        isNewData = true
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

    override fun sendReadyMessage() {
        val message = gson.toJson(ReadyMessage(1))
        if (client.isOpen) {
            client.send(message)
        }
    }

    override fun onViewCreated() {}

    override fun startGameLoop() {}

    override fun stopGameLoop() {}

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

        updatePositions()
    }

    private fun updatePositions() {
        isNewData = false

        val startTime = System.currentTimeMillis()

        uiScope.launch {
            while (!isNewData) {

                //val player = parserJSONToKotlin.playerView
                //val currentTime = System.currentTimeMillis()
                //val elapsedTime = (currentTime - startTime) / 1000f

                camera.updatePosition(objectsViews)
                //println("${objectsViews[1].y}")

                //camera.changePositionY(objects, player)

                _gameState.postValue(GameState(
                    Type.GAME,
                    //parserJSONToKotlin.getObjectsViews(),
                    objectsViews,
                    emptyList()
                ))

                //camera.updatePositions(player)
            }
        }
    }

    override fun onShot(startX: Float) {
        sendFireMessage()
    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onSensorDataChanged(deltaX: Float) {
        sendMoveMessage(deltaX)
    }

    override fun onDestroy() {
        client.close()
    }
}