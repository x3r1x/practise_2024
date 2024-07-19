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
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.view.ObjectView

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
        type: Int,
        x: Float,
        y: Float,
        speedX: Float,
        speedY: Float,
        animationTime: Int = 0
    ) : ObjectView {
        val bitmap = getBitmap(type, animationTime)
        val rect = getRect(x, y, bitmap)
        val matrix = getMatrix(rect)
        val paint = getPaint(type, animationTime)

        return ObjectView(x, y, bitmap, matrix, paint)
    }

    private fun getPaint(type: Int, animationTime: Int) : Paint {
        val paint = Paint()

        if (type == ObjectType.DISAPPEARING_PLATFORM_TYPE) {
            paint.colorFilter = PorterDuffColorFilter(animationTime, PorterDuff.Mode.MULTIPLY)
        }

        return paint
    }

    private fun getMatrix(destRect: RectF) : Matrix {
        val matrix = Matrix()
        matrix.postTranslate(destRect.left, destRect.top)

        return matrix
    }

    private fun getRect(x: Float, y: Float, bitmap: Bitmap) : RectF {
        return RectF(x - bitmap.width / 2, y - bitmap.height / 2, x + bitmap.width / 2, y + bitmap.height / 2)
    }

    private fun getBitmap(type: Int, animationTime: Int) : Bitmap {
        when(type) {
            ObjectType.STATIC_PLATFORM_TYPE -> return staticPlatformBitmap
            ObjectType.DISAPPEARING_PLATFORM_TYPE -> return disappearingPlatformBitmap
            ObjectType.MOVING_PLATFORM_ON_X_TYPE -> return movingOnXPlatformBitmap
            ObjectType.MOVING_PLATFORM_ON_Y_TYPE -> return movingOnYPlatformBitmap
            else -> when (animationTime) {
                0 -> return breakingPlatformBitmaps[0]
                1 -> return breakingPlatformBitmaps[1]
                2 -> return breakingPlatformBitmaps[2]
                3 -> return breakingPlatformBitmaps[3]
                4 -> return breakingPlatformBitmaps[4]
                else -> return breakingPlatformBitmaps[0] // TODO: костыль
            }
        }
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}