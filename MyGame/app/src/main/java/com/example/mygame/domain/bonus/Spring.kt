package com.example.mygame.domain.bonus

import android.animation.ValueAnimator
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor

class Spring(
             createdX: Float,
             createdY: Float
) : IMoveable, IBonus, IGameObject {
    var currentFrame = 0

    private var isStretchRunning = false
    private var animator : ValueAnimator? = null

    override var x = createdX
    override var y = createdY

    override var left = x - WIDTH / 2
    override var right = x + WIDTH / 2
    override var top = y - WIDTH / 2
    override var bottom = y + WIDTH / 2

    override var isDisappeared = false

    fun runStretchAnimation() {
        if (!isStretchRunning) {
            isStretchRunning = true
            animator = ValueAnimator.ofInt(0, STATE_COUNT - 1).apply {
                this.duration = ANIMATION_DURATION
                addUpdateListener { animator ->
                    currentFrame = animator.animatedValue as Int
                    setPosition(x, y - ANIMATION_HEIGHT_VALUE)
                }
            }
            animator?.start()
        }
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
        left = x - WIDTH / 2
        right = x + WIDTH / 2
        top = y - HEIGHT / 2
        bottom = y + HEIGHT /2
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    companion object {
        private const val WIDTH = 78f
        private const val HEIGHT = 53f

        private const val ANIMATION_HEIGHT_VALUE = 5f

        private const val STATE_COUNT = 4

        private const val ANIMATION_DURATION : Long = 150
    }
}