package com.example.mygame.domain.bonuses

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor

class Spring(private val initBitmap: MutableList<Bitmap>,
             createdX: Float,
             createdY: Float
) : IDrawable, IMoveable, IBonus, IGameObject {
    private var currentFrame = 0
    private var bitmap = initBitmap[currentFrame]
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
            animator = ValueAnimator.ofInt(0, initBitmap.size - 1).apply {
                this.duration = ANIMATION_DURATION
                addUpdateListener { animator ->
                    currentFrame = animator.animatedValue as Int
                    bitmap = initBitmap[currentFrame]
                    setPosition(x, y - ANIMATION_HEIGHT_VALUE)
                }
            }
            animator?.start()
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, left, top, null)
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
        private const val ANIMATION_DURATION : Long = 150
    }
}