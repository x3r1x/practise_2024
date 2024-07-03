package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable

class Platform(createdX: Float, createdY: Float) : IDrawable, ICollidable {
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

    override var x = createdX
    override var y = createdY

    override val left
        get() = x - width / 2
    override val right
        get() = x + width / 2
    override val top
        get() = y - height / 2
    override val bottom
        get() = y + height / 2

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, borderColor)
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, platformColor)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePositionX(newX: Float) {
        x = newX
    }

    override fun updatePositionY(previousY: Float, elapsedTime: Float) {}

    override fun onObjectCollide(obj: ICollidable) {
    }

    override fun onScreenCollide(screen: Screen) {
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        other ?: return false

        return !(right <= other.left ||
                left >= other.right ||
                bottom <= other.top ||
                top >= other.bottom)
    }
}