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
    var x: Float
    var y: Float
    var speedX: Float
    var speedY: Float
}

data class PlayerJSON(
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    var directionX: Int,
    var directionY: Int,
    var isWithShield: Int,
    var isShot: Int,
    var isDead: Int,
    var id: Int
) : IGameObjectJSON

data class PlatformJSON(
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int,
    val animationTime: Int
) : IGameObjectJSON

data class EnemyJSON(
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int
) : IGameObjectJSON

data class BonusJSON(
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float,
    val type: Int,
    val animationTime: Int
) : IGameObjectJSON

data class BulletJSON(
    override var x: Float,
    override var y: Float,
    override var speedX: Float,
    override var speedY: Float
) : IGameObjectJSON

data class GameData(
    @SerializedName("obj") val objects: Array<Array<Any>>,
    @SerializedName("s") val state: String
)