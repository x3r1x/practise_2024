package com.example.mygame.multiplayer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.UI.IDrawable
import com.google.gson.annotations.SerializedName

data class ClientMessage(val dx: Float, val tap: Boolean)

data class PlayerJSON(val id: String, val x: Float, val y: Float) {}

data class Platform(var x: Float, var y: Float): IDrawable {
    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, 50f, paint)
    }
}

data class Enemy(var x: Float, var y: Float): IDrawable {
    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, 50f, paint)
    }
}

data class Bonus(var x: Float, var y: Float): IDrawable {
    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, 50f, paint)
    }
}



data class Objects(
    @SerializedName("plr") val players: List<PlayerJSON>,
    @SerializedName("pla") val platforms: List<Platform>,
    @SerializedName("enm") val enemies: List<Enemy>,
    @SerializedName("bns") val bonuses: List<Bonus>,
)

data class GameData(
    @SerializedName("obj") val objects: Objects,
    @SerializedName("gam") val state: String
)