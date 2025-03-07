package com.example.mygame.domain.bullet

import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor

class Bullet(
    override var x: Float,
    override var y: Float,
    private val angle: Float
) : IMoveable, IGameObject {
    private var speedY = 0f

    override var isDisappeared = false

    override val left: Float
        get() = x - RADIUS / 2
    override val right: Float
        get() = x + RADIUS / 2
    override val top: Float
        get() = y - RADIUS / 2
    override val bottom: Float
        get() = y + RADIUS / 2



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

    companion object {
        private const val RADIUS = 50f
        private const val DEFAULT_ACCELERATION = 2200f
    }
}
