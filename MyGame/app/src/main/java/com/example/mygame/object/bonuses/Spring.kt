package com.example.mygame.`object`.bonuses

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.mygame.R
import com.example.mygame.`interface`.IBonus
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player

class Spring(private val initBitmap: MutableList<Bitmap>,
             createdX: Float,
             createdY: Float
) : IDrawable, IMoveable, IBonus, IGameObject {
    private val animationDuration : Long = 150

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
                this.duration = animationDuration
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

    override fun updatePositionX(newX: Float) {}
    override fun updatePositionY(elapsedTime: Float) {}

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    companion object {
        private const val WIDTH = 78f
        private const val HEIGHT = 53f
        private const val ANIMATION_HEIGHT_VALUE = 5f
    }
}