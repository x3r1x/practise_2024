package com.example.mygame.`object`.platforms

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`object`.Platform

class DisappearingPlatform(
    initBitmap: Bitmap,
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    init {
        this.bitmap = initBitmap
    }

    var platformColor = Paint().apply {
        color = bitmap.getPixel(bitmap.width / 2, bitmap.height / 2) //Color.YELLOW
    }

    var isDestroying = false

    fun animatePlatformColor() {
        if (!isDestroying) {
            val colorAnimator = ValueAnimator.ofArgb(platformColor.color, endColor).apply {
                this.duration = colorChangeDuration
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

    private val colorChangeDuration: Long = 2000
    private val disappearingDuration: Long = 800

    private val transparentColor = Color.TRANSPARENT
    private val endColor = Color.RED

    private fun runDisappearingAnimation() {
        val platformColorAnimator = ValueAnimator.ofArgb(platformColor.color, transparentColor).apply {
            this.duration = disappearingDuration
            addUpdateListener { animator ->
                val currentColor = animator.animatedValue as Int
                paint.color = currentColor
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isDestroying = true
                }
            })
        }

        platformColorAnimator.start()
    }

    // TODO: вынести  в отдельный файл ?
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

            val redModified = (red * Color.red(color)) / 255
            val greenModified = (green * Color.green(color)) / 255
            val blueModified = (blue * Color.blue(color)) / 255

            pixels[i] = Color.argb(alpha, redModified, greenModified, blueModified)
        }

        val modifiedBitmap = Bitmap.createBitmap(width, height, bitmap.config)
        modifiedBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return modifiedBitmap
    }
}