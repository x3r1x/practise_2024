package com.example.mygame.multiplayer

import com.google.gson.annotations.SerializedName

data class ClientMessage(
    val type: String,
    val dx: Float,
    val tap: Boolean
)

data class Ping(
    val type: String,
    val curr: Long,
)

data class ServerResponse(val type: String)


data class GameData(
    @SerializedName("obj") val objects: Array<Array<Any>>,
    @SerializedName("s") val state: String
)