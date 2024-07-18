package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView

class EnemyViewFactory(resources: Resources) {
    private val bullyBitmap = BitmapFactory.decodeResource(resources, R.drawable.bully, BITMAP_OPTIONS)
    private val flyBitmap = BitmapFactory.decodeResource(resources, R.drawable.fly, BITMAP_OPTIONS)
    private val ninjaBitmap = BitmapFactory.decodeResource(resources, R.drawable.ninja, BITMAP_OPTIONS)

    fun getEnemyView(
        x: Float,
        y: Float,
        type: String
    ) : ObjectView {
        val bitmap = getBitmap(type)
        val rect = getRect(x, y, bitmap)
        val matrix = getMatrix(rect)

        return ObjectView(x, y, bitmap, matrix)
    }

    private fun getBitmap(type: String) : Bitmap {
        when (type) {
            BULLY -> return bullyBitmap
            FLY -> return flyBitmap
            NINJA -> return ninjaBitmap
            else -> throw IllegalArgumentException("Invalid type value: $type")
        }
    }

    private fun getRect(x: Float, y: Float, bitmap: Bitmap) : RectF {
        return RectF(x - bitmap.width / 2, y - bitmap.height / 2, x + bitmap.width / 2, y + bitmap.height / 2)
    }

    private fun getMatrix(destRect: RectF) : Matrix {
        val matrix = Matrix()
        matrix.postTranslate(destRect.left, destRect.top)

        return matrix
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        const val BULLY = "bul"
        const val FLY = "fly"
        const val NINJA = "nin"
    }
}