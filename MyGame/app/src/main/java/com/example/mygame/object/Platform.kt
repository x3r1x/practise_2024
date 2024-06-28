package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Platform : Drawable {
    val width = 220f
    val height = 45f
    private val radius = height / 2

    private val platformColor = Paint().apply {
        color = Color.GREEN
    }

    private val borderColor = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }

    var x = 0f
    var y = 0f

    var left = 0f
    var right = 0f
    var top  = 0f
    var bottom = 0f

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, borderColor)
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, platformColor)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
        left = x - width / 2
        right = x + width / 2
        top = y - height / 2
        bottom = y + height / 2
    }

    override fun updatePosition(newX: Float, newY: Float) {
    }
}