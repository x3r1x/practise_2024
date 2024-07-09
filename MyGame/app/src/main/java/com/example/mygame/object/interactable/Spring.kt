package com.example.mygame.`object`.interactable

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.mygame.R
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player

class Spring(resources: Resources) : IDrawable, IMoveable, IGameObject {
    private val firstStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[0], BITMAP_OPTIONS)
    private val secondStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[1], BITMAP_OPTIONS)
    private val thirdStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[2], BITMAP_OPTIONS)
    private val fourthStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[3], BITMAP_OPTIONS)
    private val bitmaps = mutableListOf(firstStateBitmap, secondStateBitmap, thirdStateBitmap, fourthStateBitmap)

    private val animationDuration : Long = 150

    private var currentFrame = 0
    private var bitmap = bitmaps[currentFrame]
    private var isStretchRunning = false
    private var animator : ValueAnimator? = null

    override var x = 0f
    override var y = 0f

    override var left = 0f
    override var right = 0f
    override var top = 0f
    override var bottom = 0f

    override var isDisappeared = false

    fun createOnPlatform(platform: Platform) {
        val randomPosition = (MIN_SPRING_SPAWN_X .. MAX_SPRING_SPAWN_X).random()

        setPosition(platform.left + randomPosition,platform.top - HEIGHT / 2)
    }

    fun runStretchAnimation() {
        if (!isStretchRunning) {
            isStretchRunning = true
            animator = ValueAnimator.ofInt(0, bitmaps.size - 1).apply {
                this.duration = animationDuration
                addUpdateListener { animator ->
                    currentFrame = animator.animatedValue as Int
                    bitmap = bitmaps[currentFrame]
                    setPosition(x, y - ANIMATION_HEIGHT_VALUE)
                }
            }
            animator?.start()
        }
    }

    fun throwPlayer(player: Player) {
        player.isInSpring = true
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

        private const val MIN_SPRING_SPAWN_X = 40
        private const val MAX_SPRING_SPAWN_X = 180

        private val SPRING_IMAGES = listOf(
            R.drawable.spring_state_1,
            R.drawable.spring_state_2,
            R.drawable.spring_state_3,
            R.drawable.spring_state_4
        )

        private val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}