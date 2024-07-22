package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.drawable.view.ObjectView

class BulletViewFactory(resources: Resources) {
    private val bulletBitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet, BITMAP_OPTIONS)

    fun getBulletView(
        x: Float,
        y: Float,
        speedX: Float,
        speedY: Float
    ) : ObjectView {
        val rect = getRect(x, y, bulletBitmap)
        val matrix = getMatrix(rect)

        return ObjectView(x, y, bulletBitmap, matrix)
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
    }
}