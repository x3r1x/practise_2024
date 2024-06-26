package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Ball : Drawable {
    enum class States(val value: Int) {
        JUMP_STATE(1),
        ON_PLATFORM_STATE(0),
        FALLING_STATE(2)
    }

    private val gravity = 10f

    private val speed = 5f

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    val radius = 50f

    var x = 0f;
    var y = 0f;

    var state = States.FALLING_STATE

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, ballPaint)
        canvas.drawCircle(x, y, radius, borderPaint)
    }

    override fun updatePosition(newX: Float, newY: Float) {
        x += newX * speed
        if (state == States.FALLING_STATE) {
            y += gravity
        }
    }
}