package com.example.mygame.domain

import android.graphics.Paint

open class Platform(createdX: Float, createdY: Float) : IMoveable, IGameObject {

    protected var paint = Paint()

    override var isDisappeared = false

    override var x = createdX
    override var y = createdY

    override val left
        get() = x - WIDTH / 2
    override val right
        get() = x + WIDTH / 2
    override val top
        get() = y - HEIGHT / 2
    override val bottom
        get() = y + HEIGHT / 2

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    companion object {
        const val WIDTH = 175f
        const val HEIGHT = 45f
    }
}