package com.example.mygame.domain.platform

import android.animation.ValueAnimator
import com.example.mygame.domain.Platform

class BreakingPlatform(
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    var currentFrameIndex = 0

    var isBreakRunning = false

    fun runDestructionAnimation(screenHeight: Float) {
        if (!isBreakRunning) {
            isBreakRunning = true
            val animator = ValueAnimator.ofInt(0, STATE_COUNT - 1).apply {
                this.duration = DESTRUCTION_DURATION
                addUpdateListener { animator ->
                    currentFrameIndex = animator.animatedValue as Int
                }
            }

            animator?.start()
            runFallingAnimation(screenHeight)
        }
    }

    private fun runFallingAnimation(screenHeight: Float) {
        val startY = y

        val animator = ValueAnimator.ofFloat(startY, screenHeight).apply {
            duration = FALLING_DURATION
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

        private const val STATE_COUNT = 5
    }
}