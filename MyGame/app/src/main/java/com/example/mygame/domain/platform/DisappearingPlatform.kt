package com.example.mygame.domain.platform

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.mygame.domain.Platform

class DisappearingPlatform(
    initBitmap: Bitmap,
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    init {
        this.bitmap = initBitmap
    }

    private var isRunDestroying = false

    private var platformColor = Paint().apply {
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
                    bitmap = changeBitmapColor(bitmap, currentColor)
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

    private fun changeBitmapColor(bitmap: Bitmap, color: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        for (i in pixels.indices) {
            val alpha = Color.alpha(pixels[i])
            val red = Color.red(pixels[i])
            val green = Color.green(pixels[i])
            val blue = Color.blue(pixels[i])

            val redModified = (red * Color.red(color)) / RGB_MAX
            val greenModified = (green * Color.green(color)) / RGB_MAX
            val blueModified = (blue * Color.blue(color)) / RGB_MAX

            pixels[i] = Color.argb(alpha, redModified, greenModified, blueModified)
        }

        val modifiedBitmap = Bitmap.createBitmap(width, height, bitmap.config)

        modifiedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return modifiedBitmap
    }

    companion object {
        private const val COLOR_CHANGE_DURATION: Long = 2000
        private const val DISAPPEARING_DURATION: Long = 500

        private const val RGB_MAX = 255

        private const val TRANSPARENT_COLOR = Color.TRANSPARENT
        private const val RED_COLOR = Color.RED
    }
}