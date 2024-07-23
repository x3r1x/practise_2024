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

interface IGameObjectJSON {
    var id: Int
    var x: Float
    var y: Float
    var speedX: Float
    var speedY: Float
    var prevX: Float
    var prevY: Float
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
    var isDead: Int,
    override var prevX: Float = 0f,
    override var prevY: Float = 0f,
) : IGameObjectJSON

data class PlatformJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int,
    val animationTime: Int,
    override var prevX: Float = 0f,
    override var prevY: Float = 0f,
) : IGameObjectJSON

data class EnemyJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int,
    override var prevX: Float = 0f,
    override var prevY: Float = 0f,
) : IGameObjectJSON

data class BonusJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int,
    val animationTime: Int,
    override var prevX: Float = 0f,
    override var prevY: Float = 0f,
) : IGameObjectJSON

data class BulletJSON(
    override var id: Int,
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    override var prevX: Float = 0f,
    override var prevY: Float = 0f,
) : IGameObjectJSON

data class GameData(
    @SerializedName("obj") val objects: Array<Array<Double>>,
    @SerializedName("s") val state: String
)