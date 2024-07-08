package com.example.mygame.`object`.platform

import android.animation.ValueAnimator
import android.graphics.Bitmap
import com.example.mygame.`object`.Platform

class BreakingPlatform(initBitmaps: MutableList<Bitmap>,
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    private var currentFrameIndex = 0
    private var bitmaps = mutableListOf<Bitmap>()

    init {
        bitmaps = initBitmaps
        bitmap = bitmaps[currentFrameIndex]
    }

    fun runDestructionAnimation(screenHeight: Float) {
        if (!isBreakRunning) {
            isBreakRunning = true
            val animator = ValueAnimator.ofInt(0, bitmaps.size - 1).apply {
                this.duration = DESTRUCTION_DURATION
                addUpdateListener { animator ->
                    currentFrameIndex = animator.animatedValue as Int
                    bitmap = bitmaps[currentFrameIndex]
                }
            }
            animator?.start()
            runFallingAnimation(screenHeight)
        }
    }

    private var isBreakRunning = false

    private fun runFallingAnimation(screenHeight: Float) {
        val startY = y
        val endY = screenHeight
        val animator = ValueAnimator.ofFloat(startY, endY).apply {
            duration = FALLING_DURATION // Длительность анимации в миллисекундах
            addUpdateListener { animation ->
                val currentY = animation.animatedValue as Float
                setPosition(x, currentY)
            }
        }
        animator.start()
    }

    companion object {
        private const val DESTRUCTION_DURATION : Long = 150
        private const val FALLING_DURATION : Long = 2000
    }
}