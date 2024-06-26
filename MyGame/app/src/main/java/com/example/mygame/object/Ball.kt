package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Ball : Drawable {

    private val gravity = 10f
    private val speed = 5f

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    val radius = 50f

    var x = 0f;
    var y = 0f;

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, ballPaint)
        canvas.drawCircle(x, y, radius, borderPaint)
    }

    override fun updatePosition(newX: Float, newY: Float) {
        x += newX * speed
        y += gravity
    }

    fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }
}