package com.example.mygame.domain.gameplay

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.DrawableManager
import com.example.mygame.domain.logic.SensorHandler
import com.example.mygame.multiplayer.ClientMessage
import com.example.mygame.multiplayer.JSONToKotlin
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.delay
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class MultiplayerGameplay(resources: Resources, screen: Screen) : IGameplay, SensorHandler.SensorCallback {
    private val gson = Gson()
    private val JSONToKotlin = JSONToKotlin(resources)
    private var objects: List<IDrawable> = emptyList()

    private val client: WebSocketClient
    private val serverUri = URI("ws://10.250.104.27:8080")

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState

    init {
        client = object : WebSocketClient(serverUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
            }

            override fun onMessage(message: String?) {
                message?.let {
                    try {
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
    }

    private fun sendMessage(dx: Float, tap: Boolean = false) {
        val message = gson.toJson(ClientMessage(dx, tap))
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
        objects = JSONToKotlin.getObjectsViews(message)
        _gameState.postValue(GameState(
            Type.GAME,
            JSONToKotlin.getObjectsViews(message),
            emptyList()
        ))
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