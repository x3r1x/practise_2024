package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView

class BulletViewFactory(resources: Resources) {
    private val bulletBitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet, BITMAP_OPTIONS)

    fun getBulletView(
        x: Float,
        y: Float
    ) : ObjectView {
        return ObjectView(x, y, bulletBitmap, Matrix())
    }
    
    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}