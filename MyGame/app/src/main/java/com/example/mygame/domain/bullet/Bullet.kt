package com.example.mygame.domain.bullet

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor

class Bullet(
    private val image: Bitmap,
    override var x: Float,
    override var y: Float,
    private val angle: Float
) : IMoveable, IDrawable, IGameObject {

    override var isDisappeared = false

    override val left: Float
        get() = x - RADIUS / 2
    override val right: Float
        get() = x + RADIUS / 2
    override val top: Float
        get() = y - RADIUS / 2
    override val bottom: Float
        get() = y + RADIUS / 2

    private var speedY = 0f

    fun shoot() {
        speedY = DEFAULT_ACCELERATION
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePosition(elapsedTime: Float) {
        y -= speedY * elapsedTime
        x += angle
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun draw(canvas: Canvas) {
        val matrix = Matrix()

        val destRect = RectF(left, top, right, bottom)

        val scaleX = destRect.width() / image.width
        val scaleY = destRect.height() / image.height

        matrix.postScale(scaleX, scaleY)
        matrix.postTranslate(left, top)

        canvas.drawBitmap(image, matrix, null)
    }

    companion object {
        private const val RADIUS = 50f
        private const val DEFAULT_ACCELERATION = 2200f // Ускорение по умолчанию
    }
}
