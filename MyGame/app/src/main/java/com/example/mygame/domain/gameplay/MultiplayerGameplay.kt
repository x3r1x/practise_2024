package com.example.mygame.domain.gameplay

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.domain.Screen
import com.example.mygame.domain.logic.SensorHandler
import com.example.mygame.multiplayer.ClientMessage
import com.example.mygame.multiplayer.JSONToKotlin
import com.example.mygame.multiplayer.Ping
import com.example.mygame.multiplayer.ServerResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class MultiplayerGameplay(resources: Resources, screen: Screen) : IGameplay, SensorHandler.SensorCallback {
    private val uiScope = CoroutineScope(Dispatchers.Main)

    private var isNewData = false

    private val gson = Gson()
    private val parserJSONToKotlin = JSONToKotlin(resources)

    private var pingSentTime: Long = 0

    private val client: WebSocketClient
    private val serverUri = URI("ws://10.250.104.162:8080")

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState

    init {
        client = object : WebSocketClient(serverUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d("WebSocket", "Connection opened")
            }

            override fun onMessage(message: String?) {
                message?.let {
                    try {
                        val serverResponse = gson.fromJson(message, ServerResponse::class.java)
                        if (serverResponse.type == "pong") {
                            val currentTime = System.currentTimeMillis()
                            val ping = currentTime - pingSentTime
                            Log.d("WebSocket", "Ping: $ping ms")
                        } else {
                            isNewData = true
                            handleServerData(message)
                        }
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
    }

    private fun sendMessage(dx: Float, tap: Boolean = false) {
        val ping = gson.toJson(Ping("ping", 0))
        if (client.isOpen) {
            pingSentTime = System.currentTimeMillis()
            client.send(ping)
        }
        val message = gson.toJson(ClientMessage("msg", dx, tap))
        if (client.isOpen) {
            client.send(message)
        }
    }

    fun connect() {
        client.connect()
    }

    fun close() {
        client.close()
    }

    private fun handleServerData(message: String) {
        _gameState.postValue(GameState(
            Type.GAME,
            parserJSONToKotlin.getObjectsViews(message),
            emptyList()
        ))

        updatePositions()
    }

    private fun updatePositions() {
        isNewData = false

        uiScope.launch {
            while (!isNewData) {
                _gameState.postValue(GameState(
                    Type.GAME,
                    parserJSONToKotlin.interpolation(),
                    emptyList()
                ))
                // При необходимости добавить паузу
            }
        }
    }

    override fun onShot(startX: Float) {
        sendMessage(0f, true)
    }

    override fun onPause() {
    }

    override fun onResume() {
    }

    override fun onSensorDataChanged(deltaX: Float) {
        sendMessage(deltaX)
    }

    override fun onDestroy() {
        client.close()
    }
}