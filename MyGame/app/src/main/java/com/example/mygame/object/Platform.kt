package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Platform(private val startX: Float, private val startY: Float) : Drawable {
    val x = startX
    val y = startY
    val width = 220f
    val height = 45f

    private val platformColor = Paint().apply {
        color = Color.GREEN
    }

    private val borderColor = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    override fun draw(canvas: Canvas) {
        val radius = height / 2
        val topLeftX = x + radius
        val topLeftY = y
        val bottomRightX = x + width - radius
        val bottomRightY = y + height

        canvas.drawCircle(x + radius, y + radius, radius, borderColor)
        canvas.drawRect(topLeftX, topLeftY, bottomRightX, bottomRightY, borderColor)
        canvas.drawCircle(x + width - radius, y + radius, radius, borderColor)

        canvas.drawCircle(x + radius, y + radius, radius, platformColor)
        canvas.drawRect(topLeftX, topLeftY, bottomRightX, bottomRightY, platformColor)
        canvas.drawCircle(x + width - radius, y + radius, radius, platformColor)
    }

    override fun updatePosition(newX: Float, newY: Float) {
    }
}