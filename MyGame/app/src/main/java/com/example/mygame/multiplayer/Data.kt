package com.example.mygame.multiplayer

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.UI.IDrawable

data class ClientMessage(val dx: Float, val tap: Boolean)

data class Player(var x: Float, var y: Float): IDrawable {
    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x,y, 50f, paint)
    }
}

data class Platform(var x: Float, var y: Float): IDrawable {
    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x,y, 50f, paint)
    }
}

data class Enemy(var x: Float, var y: Float): IDrawable {
    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x,y, 50f, paint)
    }
}

data class Bonus(var x: Float, var y: Float): IDrawable {
    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x,y, 50f, paint)
    }
}

data class GameData(
    val players: List<Player>,
    val platforms: List<Platform>,
    val enemies: List<Enemy>,
    val bonuses: List<Bonus>,
    val gameStatus: String
)