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

interface IObjectJSON {
    val type: Int
    val posX: Float
    val posY: Float
    val speedX: Float
    val speedY: Float
}

data class PlayerJSON(
    override val type: Int,
    override val posX: Float,
    override val posY: Float,
    override val speedX: Float,
    override val speedY: Float,
    val directionX: Int,
    val directionY: Int,
    val isWithShield: Boolean,
    val isShooting: Boolean,
    val isDead: Boolean,
    val score: Double
) : IObjectJSON

data class PlatformJSON(
    override val type: Int,
    override val posX: Float,
    override val posY: Float,
    override val speedX: Float,
    override val speedY: Float,
    val animationTime: Int
) : IObjectJSON

data class EnemyJSON(
    override val type: Int,
    override val posX: Float,
    override val posY: Float,
    override val speedX: Float,
    override val speedY: Float
) : IObjectJSON

data class BonusJSON(
    override val type: Int,
    override val posX: Float,
    override val posY: Float,
    override val speedX: Float,
    override val speedY: Float,
    val animationTime: Int
) : IObjectJSON

data class BulletJSON(
    override val type: Int,
    override val posX: Float,
    override val posY: Float,
    override val speedX: Float,
    override val speedY: Float
) : IObjectJSON

data class Objects(
    val data: List<IObjectJSON>
)

data class GameData(
    @SerializedName("obj") val objects: Objects,
    @SerializedName("s") val state: String
)