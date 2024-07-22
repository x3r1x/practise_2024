package com.example.mygame.domain

import android.graphics.Paint

open class Platform(createdX: Float, createdY: Float) : IMoveable, IGameObject {
    val width = 175f
    val height = 45f

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

    protected lateinit var bitmap: Bitmap

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }
}