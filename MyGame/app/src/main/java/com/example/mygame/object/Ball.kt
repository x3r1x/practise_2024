package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Ball : Drawable {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1)
    }

    val gravity = 10f
    val maxJumpHeight = 550f // Максимальная высота прыжка

    private val speedX = 3f
    var speedY = gravity
    var directionY = DirectionY.DOWN

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    val radius = 50f

    var x = 0f
    var y = 0f
    var initialY = 0f // Начальная позиция по оси Y для отслеживания высоты прыжка

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, ballPaint)
        canvas.drawCircle(x, y, radius, borderPaint)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
        initialY = startY // Устанавливаем начальную позицию по Y
    }

    override fun updatePosition(newX: Float, newY: Float) {
        x += newX * speedX
        y += speedY * directionY.value
        updateSpeedY()
    }

    private fun updateSpeedY() {
        when (directionY) {
            DirectionY.UP -> {
                speedY -= gravity
                if (initialY - y >= maxJumpHeight) {
                    directionY = DirectionY.DOWN
                    speedY = gravity
                }
            }
            DirectionY.DOWN -> {
                speedY = gravity
            }
        }
    }
}
