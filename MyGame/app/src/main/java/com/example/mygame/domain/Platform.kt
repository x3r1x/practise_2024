package com.example.mygame.domain

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.player.Player.DirectionX

open class Platform(createdX: Float, createdY: Float) : IDrawable, IMoveable, IGameObject {
    val width = 175f
    val height = 45f

    protected lateinit var bitmap: Bitmap
    protected var paint = Paint()

    override var isDisappeared = false

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

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun draw(canvas: Canvas) {
        val rect = getRect()
        val matrix = getMatrix(rect, bitmap)

        canvas.drawBitmap(bitmap, matrix, paint)
    }

    private fun getMatrix(destRect: RectF, image: Bitmap) : Matrix {
        val matrix = Matrix()
        val scaleX = destRect.width() / image.width
        val scaleY = destRect.height() / image.height

        matrix.postScale(scaleX, scaleY)
        matrix.postTranslate(destRect.left, destRect.top)

        return matrix
    }

    private fun getRect() : RectF {
        return RectF(left, top, right, bottom)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }
}