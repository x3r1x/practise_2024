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

interface IGameObjectJSON {
    var id: Int
    var x: Float
    var y: Float
    var speedX: Float
    var speedY: Float
}

data class PlayerJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    var directionX: Int,
    var directionY: Int,
    var isWithShield: Int,
    var isShot: Int,
    var isDead: Int
) : IGameObjectJSON

data class PlatformJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int,
    val animationTime: Int
) : IGameObjectJSON

data class EnemyJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int
) : IGameObjectJSON

data class BonusJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int,
    val animationTime: Int
) : IGameObjectJSON

data class BulletJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float
) : IGameObjectJSON

data class GameData(
    @SerializedName("obj") val objects: Array<Array<Double>>,
    @SerializedName("s") val state: String
)

data class PlayerIdFromServer(
    @SerializedName("id") val id: Int
)