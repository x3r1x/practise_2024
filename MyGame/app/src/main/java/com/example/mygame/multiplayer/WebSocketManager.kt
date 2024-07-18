package com.example.mygame.multiplayer

import android.content.Context
import android.util.Log
import com.example.mygame.domain.logic.SensorHandler
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class WebSocketManager(context: Context, serverUri: URI): SensorHandler.SensorCallback {
    private val gson = Gson()
    private var client: WebSocketClient? = null

    private val sensorHandler = SensorHandler(context, this)

    init {
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
    }

    fun connect() {
        client?.connect()
    }

    private fun sendMessage(dx: Float, tap: Boolean = false) {
        val message = gson.toJson(ClientMessage(dx, tap))
        if (client?.isOpen == true) {
            client?.send(message)
        }
    }

    fun close() {
        client?.close()
    }

    private fun handleServerData(data: String) {
    }

    override fun onSensorDataChanged(deltaX: Float) {
        sendMessage(deltaX)
    }
}
