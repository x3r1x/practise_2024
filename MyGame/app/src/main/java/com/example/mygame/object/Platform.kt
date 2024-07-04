package com.example.mygame.`object`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable

open class Platform(createdX: Float, createdY: Float) : IDrawable, ICollidable {
    val width = 220f
    val height = 45f

    protected lateinit var bitmap: Bitmap
    protected var paint = Paint()

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
        canvas.drawBitmap(bitmap, left, top, paint)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePositionX(newX: Float) {
        x = newX
    }

    override fun updatePositionY(elapsedTime: Float) {
    }

    override fun onObjectCollide(obj: ICollidable) {
    }

    override fun onScreenCollide(screen: Screen) {
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        other ?: return false

        if (other is Screen) {
            return top >= other.bottom
        }

        return !(right <= other.left ||
                left >= other.right ||
                bottom <= other.top ||
                top >= other.bottom)
    }
}