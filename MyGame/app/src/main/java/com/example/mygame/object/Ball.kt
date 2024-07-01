package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.Physics
import com.example.mygame.`interface`.Drawable

class Ball : Drawable {
    enum class DirectionY(val value: Int) {
        UP(-1),
        STILL(0),
        DOWN(1),
    }

    val radius = 50f
    val jumpTime = 50f

    var x = 0f
    var y = 0f
    var initialY = 0f
    var speedY = Physics.GRAVITY
    var directionY = DirectionY.DOWN

    var jumpHeight = 0f
    var jumpSpeed = 0f
    var fallBoost = 0f

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private val speedX = 2.5f

    fun updateJumpHeight(screenHeight: Float) {
        if (Physics.MAX_JUMP_PIXELS_FROM_BOTTOM - (screenHeight - y) > Physics.MAX_JUMP_HEIGHT) {
            jumpHeight = Physics.MAX_JUMP_HEIGHT
        } else {
            jumpHeight = Physics.MAX_JUMP_PIXELS_FROM_BOTTOM - (screenHeight - y)
        }
    }

    fun updateSpeedAndBoost() {
        jumpSpeed = Physics(0f).getStartCollisionSpeed(jumpHeight, jumpTime)
        fallBoost = Physics(0f).getJumpBoost(jumpSpeed, jumpTime)
        speedY = jumpSpeed
    }

    private fun updateSpeedY() {
        when (directionY) {
            DirectionY.UP -> {
                speedY -= fallBoost
                if (initialY - y >= jumpHeight) {
                    directionY = DirectionY.DOWN
                    speedY = Physics.GRAVITY
                }
            }

            DirectionY.DOWN -> {
                speedY += Physics.GRAVITY * Physics.GRAVITY_RATIO
            }

            DirectionY.STILL -> {
                directionY = DirectionY.UP
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
}