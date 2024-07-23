package com.example.mygame.domain.platform.factory

import android.graphics.BitmapFactory
import com.example.mygame.domain.platform.Platform

interface IPlatformFactory {
    fun generatePlatform(createdX: Float, createdY: Float) : Platform

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}