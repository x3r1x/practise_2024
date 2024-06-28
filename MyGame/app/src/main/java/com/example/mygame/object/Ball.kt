package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.Physics
import com.example.mygame.`interface`.Drawable

class Ball : Drawable {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1),
    }

    val jumpSpeed = Physics(0f).getStartCollisionSpeed(MAX_JUMP_HEIGHT, FALL_BOOST)
    val radius = 50f

    var x = 0f
    var y = 0f
    var initialY = 0f
    var speedY = Physics.GRAVITY
    var directionY = DirectionY.DOWN

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val speedX = 2.5f

    private fun updateSpeedY() {
        when (directionY) {
            DirectionY.UP -> {
                speedY -= FALL_BOOST
                if (initialY - y >= MAX_JUMP_HEIGHT) {
                    directionY = DirectionY.DOWN
                    speedY = Physics.GRAVITY
                }
            }

            DirectionY.DOWN -> {
                speedY += Physics.GRAVITY * Physics.GRAVITY_RATIO
            }
        }
    }

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

    companion object {
        private const val MAX_JUMP_HEIGHT = 600f // Максимальная высота прыжка
        private const val FALL_BOOST = 0.5f
    }
}