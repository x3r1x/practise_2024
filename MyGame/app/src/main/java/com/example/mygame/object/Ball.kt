package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable
import kotlin.math.sqrt

class Ball : Drawable {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1)
    }

    val gravity = 7.5f
    val radius = 50f
    val maxJumpHeight = 600f // Максимальная высота прыжка
    val jumpTime = 1.5f
    val jumpSpeed = sqrt(gravity * (2 * maxJumpHeight - gravity))

    var x = 0f
    var y = 0f
    var initialY = 0f
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

    private val speedX = 4f

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
