package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Platform(private val x: Float, private val y: Float) : Drawable {
    val startX = x
    val startY = y
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
        val topLeftX = startX + radius
        val topLeftY = startY
        val bottomRightX = startX + width - radius
        val bottomRightY = startY + height

        canvas.drawCircle(startX + radius, startY + radius, radius, borderColor)
        canvas.drawRect(topLeftX, topLeftY, bottomRightX, bottomRightY, borderColor)
        canvas.drawCircle(startX + width - radius, startY + radius, radius, borderColor)

        canvas.drawCircle(startX + radius, startY + radius, radius, platformColor)
        canvas.drawRect(topLeftX, topLeftY, bottomRightX, bottomRightY, platformColor)
        canvas.drawCircle(startX + width - radius, startY + radius, radius, platformColor)
    }

    override fun updatePosition(newX: Float, newY: Float) {

    }
}