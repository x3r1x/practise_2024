package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView
import android.graphics.Paint
import android.graphics.RectF
import com.example.mygame.domain.drawable.factory.PlayerViewFactory.Companion.DIRECTION_X_LEFT
import com.example.mygame.domain.platform.factory.IPlatformFactory
import com.example.mygame.domain.platform.factory.IPlatformFactory.Companion

class PlatformViewFactory(private val resources: Resources) {
    private val staticPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.static_platform)
    private val movingOnYPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.moving_platform_on_y)
    private val movingOnXPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.moving_platform_on_x)
    private val disappearingPlatformBitmap = BitmapFactory.decodeResource(resources, R.drawable.disappearing_platform)
    private val breakingPlatformBitmaps = listOf(
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_1_state),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_2_state),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_3_state),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_4_state),
        BitmapFactory.decodeResource(resources, R.drawable.break_platform_5_state)
    )

    fun getPlatformView(
        x: Float,
        y: Float,
        type: String,
        another: Int
    ) : ObjectView {
        val bitmap = getBitmap(type, another)
        val rect = getRect(x, y)
        val matrix = getMatrix(rect, bitmap)
        val paint = Paint()

        if (type == DISAPPEARING) {
            paint.color = another
        }

        return ObjectView(x, y, bitmap, matrix, paint)
    }

    private fun getMatrix(destRect: RectF, bitmap: Bitmap) : Matrix {
        val scaleX = destRect.width() / bitmap.width
        val scaleY = destRect.height() / bitmap.height

        val matrix = Matrix()

        matrix.postScale(scaleX, scaleY)
        matrix.postTranslate(destRect.left, destRect.top)

        return matrix
    }

    private fun getRect(x: Float, y: Float) : RectF {
        return RectF(x - WIDTH / 2, y - HEIGHT / 2, x + WIDTH / 2, y + HEIGHT / 2)
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
        const val WIDTH = 175f
        const val HEIGHT = 45f

        const val STATIC = "static"
        const val BREAKING = "breaking"
        const val DISAPPEARING = "disappearing"
        const val MOVING_ON_X = "movingOnX"
        const val MOVING_ON_Y = "movingOnY"
    }
}