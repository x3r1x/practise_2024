package com.example.mygame.domain.platform

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Paint

class DisappearingPlatform(
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {

    private var isRunDestroying = false

    var platformColor = Paint().apply {
        color = Color.YELLOW
    }

    fun animatePlatformColor() {
        if (!isRunDestroying) {
            isRunDestroying = true

            val colorAnimator = ValueAnimator.ofArgb(platformColor.color, RED_COLOR).apply {
                this.duration = COLOR_CHANGE_DURATION
                addUpdateListener { animator ->
                    val currentColor = animator.animatedValue as Int
                    platformColor.color = currentColor
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        runDisappearingAnimation()
                    }
                })
            }

            colorAnimator.start()
        }
    }

    private fun runDisappearingAnimation() {
        val platformColorAnimator = ValueAnimator.ofArgb(platformColor.color, TRANSPARENT_COLOR).apply {
            this.duration = DISAPPEARING_DURATION
            addUpdateListener { animator ->
                val currentColor = animator.animatedValue as Int
                platformColor.color = currentColor
                paint.color = currentColor
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isDisappeared = true
                }
            })
        }

        platformColorAnimator.start()
    }

    companion object {
        private const val COLOR_CHANGE_DURATION: Long = 2000
        private val DISAPPEARING_DURATION: Long = 500

        private val TRANSPARENT_COLOR = Color.TRANSPARENT
        private val RED_COLOR = Color.RED
    }
}