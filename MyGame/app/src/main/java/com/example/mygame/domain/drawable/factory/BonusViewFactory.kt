package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.view.ObjectView

class BonusViewFactory(resources: Resources) {
    private val shieldBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.shield, BITMAP_OPTIONS)
    private val springBitmaps = listOf(
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_1, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_2, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_3, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_4, BITMAP_OPTIONS)
    )
    private val jetpackBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.jetpack, BITMAP_OPTIONS)

    fun getBonusView(
        type: Int,
        x: Float,
        y: Float,
        animationTime: Int
    ): ObjectView {
        val bitmap = getBitmap(type, animationTime)
        val rect = getRect(x, y, bitmap)
        val matrix = getMatrix(rect)

        return ObjectView(x, y, bitmap, matrix)
    }

    private fun getBitmap(type: Int, animationTime: Int): Bitmap {
        when (type) {
            ObjectType.SHIELD_TYPE -> return shieldBitmap
            ObjectType.JETPACK_TYPE -> return jetpackBitmap
            else -> when (animationTime) {
                0 -> return springBitmaps[0]
                1 -> return springBitmaps[1]
                2 -> return springBitmaps[2]
                3 -> return springBitmaps[3]
                else -> throw IllegalArgumentException("Invalid spring animation value: $animationTime")
            }
        }
    }

    private fun getMatrix(destRect: RectF): Matrix {
        val matrix = Matrix()
        matrix.postTranslate(destRect.left, destRect.top)

        return matrix
    }

    private fun getRect(x: Float, y: Float, bitmap: Bitmap): RectF {
        return RectF(
            x - bitmap.width / 2,
            y - bitmap.height / 2,
            x + bitmap.width / 2,
            y + bitmap.height / 2
        )
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}