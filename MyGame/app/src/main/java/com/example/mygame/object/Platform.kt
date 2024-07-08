package com.example.mygame.`object`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IMoveable

open class Platform(createdX: Float, createdY: Float) : IDrawable, ICollidable, IMoveable, IGameObject {
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
    }

    override fun updatePositionY(elapsedTime: Float) {
    }

    override fun onObjectCollide(obj: ICollidable) {
    }

    override fun onScreenCollide(screen: Screen) {
        // Удаление из списка
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        other ?: return false

        return !(right <= other.left ||
                left >= other.right ||
                bottom <= other.top ||
                top >= other.bottom)
    }
}