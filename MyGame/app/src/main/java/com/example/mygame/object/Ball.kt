package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Ball : Drawable {
    val radius = 50f
    val gravity = 10f
    private val speedX = 5f
    var speedY = gravity

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    var x = 0f;
    var y = 0f;
    var isOnPlatform = false

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, ballPaint)
        canvas.drawCircle(x, y, radius, borderPaint)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePosition(newX: Float, newY: Float) {
        x += newX * speedX
        if (!isOnPlatform) {
            y += speedY
        }
    }

    fun updateSpeedY(additionalSpeed: Float) {
        if (speedY + additionalSpeed >= gravity) {
            speedY = gravity
        } else {
            speedY += gravity
        }
    }
}