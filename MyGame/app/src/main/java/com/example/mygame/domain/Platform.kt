package com.example.mygame.domain

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.mygame.UI.IDrawable

open class Platform(createdX: Float, createdY: Float) : IDrawable, IMoveable, IGameObject {
    val width = 220f
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
        canvas.drawBitmap(bitmap, left, top, paint)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }
}