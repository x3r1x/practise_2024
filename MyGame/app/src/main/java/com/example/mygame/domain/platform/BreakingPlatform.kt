package com.example.mygame.domain.platform

import android.animation.ValueAnimator
import android.os.CountDownTimer

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
            runFallingAnimation()
        }
    }

    private fun runFallingAnimation() {
        val animator = object : CountDownTimer(MAX_ANIMATION_LENGTH, ANIMATION_TICK) {
            override fun onTick(p0: Long) {
                setPosition(x, y + FALL_OFFSET)
            }

            override fun onFinish() {}
        }

        animator.start()
    }

    companion object {
        private const val DESTRUCTION_DURATION : Long = 150

        private const val FALL_OFFSET = 10f
        private const val MAX_ANIMATION_LENGTH : Long = 2000
        private const val ANIMATION_TICK : Long = 10

        private const val STATE_COUNT = 5
    }
}