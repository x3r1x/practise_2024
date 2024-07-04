package com.example.mygame.`object`.iteractable

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.mygame.R
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.Platform

class Shield(resources: Resources) : IDrawable {
    private val bitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTIONS)

    private var left = 0f
    private var right = 0f
    private var top = 0f
    private var bottom = 0f

    override var x: Float = 0f
    override var y: Float = 0f


    fun createOnPlatform(platform: Platform) {
        setPosition(platform.x, platform.top - SIDE / 2 - SHIELD_OFFSET)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, left, top, null)
    }

    override fun setPosition(newX: Float, newY: Float) {
        x = newX
        y = newY
        left = x - SIDE / 2
        right = x + SIDE / 2
        top = y - SIDE / 2
        bottom = y + SIDE / 2
    }

    override fun updatePositionX(newX: Float) {}
    override fun updatePositionY(elapsedTime: Float) {}

    companion object {
        private const val SHIELD_OFFSET = 25f
        private const val SIDE = 100f

        private val IMAGE = R.drawable.shield

        private val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}