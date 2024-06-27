package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Platform : Drawable {
    var x = 0f
    var y = 0f
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
        val topLeftX = x
        val topLeftY = y
        val bottomRightX = x + width
        val bottomRightY = y + height
        val radius = height / 2

        canvas.drawRoundRect(topLeftX, topLeftY, bottomRightX, bottomRightY, radius, radius, borderColor)
        canvas.drawRoundRect(topLeftX, topLeftY, bottomRightX, bottomRightY, radius, radius, platformColor)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePosition(newX: Float, newY: Float) {
    }
}