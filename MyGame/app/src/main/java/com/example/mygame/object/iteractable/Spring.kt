package com.example.mygame.`object`.iteractable

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player.DirectionX
import com.example.mygame.`object`.Screen

class Spring(createdX: Float, createdY: Float, resources: Resources) : IDrawable, ICollidable {
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

    override var x = createdX
    override var y = createdY

    override val top: Float
        get() = y - HEIGHT / 2

    override val bottom: Float
        get() = y + HEIGHT / 2

    override val left: Float
        get() = x - WIDTH / 2

    override val right: Float
        get() = x + WIDTH / 2

    fun moveSpringToPlatform(platform: Platform) {
        val platformBorderRadius = 20
        val randomPosition = (0 .. platform.width.toInt() - platformBorderRadius * 2).random()

        setPosition(platform.left + platformBorderRadius + randomPosition ,platform.top - HEIGHT / 2)
    }

    private fun runStretchAnimation() {
        if (!isStretchRunning) {
            isStretchRunning = true
            animator = ValueAnimator.ofInt(0, bitmaps.size - 1).apply {
                this.duration = animationDuration
                addUpdateListener { animator ->
                    currentFrame = animator.animatedValue as Int
                    bitmap = bitmaps[currentFrame]
                }
            }
            animator?.start()
        }
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        other ?: return false

        return !(right <= other.left ||
                left >= other.right ||
                bottom <= other.top ||
                top >= other.bottom)
    }

    override fun onObjectCollide(obj: ICollidable) {
        runStretchAnimation()
    }

    override fun onScreenCollide(screen: Screen) {}

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, left, top, null)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePositionX(newX: Float) {}
    override fun updatePositionY(elapsedTime: Float) {}

    companion object {
        private const val WIDTH = 78f
        private const val HEIGHT = 53f

        private val SPRING_IMAGES = listOf(
            R.drawable.spring_state_1,
            R.drawable.spring_state_2,
            R.drawable.spring_state_3,
            R.drawable.spring_state_4
        )

        private val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

//        private const val FIRST_STATE_HEIGHT = 53f
//        private const val SECOND_STATE_HEIGHT = 73f
//        private const val THIRD_STATE_HEIGHT = 93f
//        private const val FOURTH_STATE_HEIGHT = 113f
    }
}