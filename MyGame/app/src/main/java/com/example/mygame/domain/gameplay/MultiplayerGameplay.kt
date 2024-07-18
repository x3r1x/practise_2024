package com.example.mygame.domain.gameplay

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.domain.Screen
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
    private lateinit var client: WebSocketClient
    private val serverUri = URI("10.250.105.20:8080")

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState

    override fun onViewCreated() {
        client = object : WebSocketClient(serverUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d("WebSocket", "Opened")
            }

            override fun onMessage(message: String?) {
                Log.d("WebSocket", "Message received: $message")
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
        // TODO: Парсинг из JSON в kotlin классы
    }

    override fun onShot(startX: Float) {
        sendMessage(0f, true)
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun onSensorDataChanged(deltaX: Float) {
        sendMessage(deltaX)
    }
}