package com.example.mygame.`object`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IVisitor

class Bullet(
    private val image: Bitmap,
    override var x: Float,
    override var y: Float
) : IMoveable, IDrawable, IGameObject {

    override var isDisappeared = false

    override val left: Float
        get() = x - WIDTH / 2
    override val right: Float
        get() = x + WIDTH / 2
    override val top: Float
        get() = y - HEIGHT / 2
    override val bottom: Float
        get() = y + HEIGHT / 2

    private var speedY = 0f

    fun shoot() {
        speedY = DEFAULT_ACCELERATION
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePositionX(newX: Float) {
    }

    override fun updatePositionY(elapsedTime: Float) {
        y -= speedY * elapsedTime
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    private val paint = Paint().apply {
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        val matrix = Matrix()

        val destRect = RectF(left, top, right, bottom)

        val scaleX = destRect.width() / image.width
        val scaleY = destRect.height() / image.height

        matrix.postScale(scaleX, scaleY)
        matrix.postTranslate(left, top)

//        canvas.drawRect(destRect, paint)
        canvas.drawBitmap(image, matrix, null)
    }

    companion object {
        private const val WIDTH = 40f
        private const val HEIGHT = 100f
        private const val DEFAULT_ACCELERATION = 1500f // Ускорение по умолчанию
    }
}
