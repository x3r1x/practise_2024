package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView

class PlatformViewFactory(resources: Resources) {
    private val staticPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.static_platform, BITMAP_OPTIONS)
    private val movingOnYPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.moving_platform_on_y, BITMAP_OPTIONS)
    private val movingOnXPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.moving_platform_on_x, BITMAP_OPTIONS)
    private val disappearingPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.disappearing_platform, BITMAP_OPTIONS)
    private val breakingPlatformBitmaps = listOf(
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_1_state, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_2_state, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_3_state, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_4_state, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_5_state, BITMAP_OPTIONS)
    )

    fun getPlatformView(
        x: Float,
        y: Float,
        type: String,
        another: Int
    ) : ObjectView {
        val bitmap = getBitmap(type, another)
        val rect = getRect(x, y, bitmap)
        val matrix = getMatrix(rect)
        val paint = Paint()

        if (type == DISAPPEARING) {
            paint.colorFilter = PorterDuffColorFilter(another, PorterDuff.Mode.MULTIPLY)
        }

        return ObjectView(x, y, bitmap, matrix, paint)
    }

    private fun getMatrix(destRect: RectF) : Matrix {
        val matrix = Matrix()
        matrix.postTranslate(destRect.left, destRect.top)

        return matrix
    }

    private fun getRect(x: Float, y: Float, bitmap: Bitmap) : RectF {
        return RectF(x - bitmap.width / 2, y - bitmap.height / 2, x + bitmap.width / 2, y + bitmap.height / 2)
    }

    private fun getBitmap(type: String, animation: Int) : Bitmap {
        when(type) {
            STATIC -> return staticPlatformBitmap
            DISAPPEARING -> return disappearingPlatformBitmap
            MOVING_ON_X -> return movingOnXPlatformBitmap
            MOVING_ON_Y -> return movingOnYPlatformBitmap
            else -> when (animation) {
                0 -> return breakingPlatformBitmaps[0]
                1 -> return breakingPlatformBitmaps[1]
                2 -> return breakingPlatformBitmaps[2]
                3 -> return breakingPlatformBitmaps[3]
                4 -> return breakingPlatformBitmaps[4]
                else -> throw IllegalArgumentException("Invalid animation value: $animation")
            }
        }
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        const val STATIC = "static"
        const val BREAKING = "breaking"
        const val DISAPPEARING = "disappearing"
        const val MOVING_ON_X = "movingOnX"
        const val MOVING_ON_Y = "movingOnY"
    }
}