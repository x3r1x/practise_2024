package com.example.mygame.multiplayer

import com.google.gson.annotations.SerializedName

interface ClientMessage {
    val T: String
}

data class InitMessage(
    val w: Int,
    val h: Int,
    override val T: String = "i"
) : ClientMessage

data class MoveMessage(
    val x: Float,
    override val T: String = "m"
) : ClientMessage

data class FireMessage(
    val t: Int,
    override val T: String = "f"
) : ClientMessage

data class ReadyMessage(
    val r: Int,
    override val T: String = "r"
) : ClientMessage

data class Ping(
    val type: String,
    val curr: Long,
)

data class ServerResponse(val type: String)

data class GameData(
    @SerializedName("obj") val objects: Array<Array<Double>>,
    @SerializedName("s") val state: String
)

data class PlayerIdFromServer(
    @SerializedName("id") val id: Int
)