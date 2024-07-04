package com.example.mygame.`interface`

import android.graphics.BitmapFactory
import com.example.mygame.`object`.Platform

interface IPlatformFactory {
    fun generatePlatform(createdX: Float, createdY: Float) : Platform

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}