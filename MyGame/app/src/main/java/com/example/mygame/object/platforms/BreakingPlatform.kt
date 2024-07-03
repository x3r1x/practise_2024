package com.example.mygame.`object`.platforms

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
    // Callback - удаление текущего объекта из листа
    fun runDestructionAnimation(screenHeight: Float, callback: () -> Unit) {
        if (!isBreakRunning) {
            isBreakRunning = true
            animator = ValueAnimator.ofInt(0, bitmaps.size - 1).apply {
                this.duration = durationBreakingAnimation
                addUpdateListener { animator ->
                    currentFrameIndex = animator.animatedValue as Int
                    bitmap = bitmaps[currentFrameIndex]
                }
            }
            animator?.start()
            runFallingAnimation(screenHeight, callback)
        }
    }

    private var isBreakRunning = false

    private var animator: ValueAnimator? = null

    private val durationBreakingAnimation : Long = 150

    private fun runFallingAnimation(screenHeight: Float, callback: () -> Unit) {
        val startY = y
        val endY = screenHeight
        val animator = ValueAnimator.ofFloat(startY, endY).apply {
            duration = 2000 // Длительность анимации в миллисекундах
            addUpdateListener { animation ->
                val currentY = animation.animatedValue as Float
                setPosition(x, currentY)
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    callback()
                }
            })
        }
        animator.start()
    }
}