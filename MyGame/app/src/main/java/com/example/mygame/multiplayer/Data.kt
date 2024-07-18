package com.example.mygame.multiplayer

import com.google.gson.annotations.SerializedName

data class ClientMessage(
    val dx: Float,
    val tap: Boolean
)

data class PlayerJSON(
    val id: String,
    val x: Float,
    val y: Float,
    val drx: Int,
    val dry: Int,
    val sld: Int,
    val sht: Int,
    val ded: Int,
    val scr: Int
)

data class PlatformJSON(
    val x: Float,
    val y: Float,
    val typ: String,
    val anm: Int
)

data class EnemyJSON(
    val x: Float,
    val y: Float,
    val typ: String,
    val anm: Int
)

data class BonusJSON(
    val x: Float,
    val y: Float,
    val typ: String,
    val anm: Int
)

data class BulletJSON(
    val x: Float,
    val y: Float
)

data class Objects(
    @SerializedName("plr") val players: List<PlayerJSON>,
    @SerializedName("pla") val platforms: List<PlatformJSON>,
    @SerializedName("enm") val enemies: List<EnemyJSON>,
    @SerializedName("bns") val bonuses: List<BonusJSON>,
    @SerializedName("blt") val bullets: List<BulletJSON>
)

data class GameData(
    @SerializedName("obj") val objects: Objects,
    @SerializedName("gam") val state: String
)