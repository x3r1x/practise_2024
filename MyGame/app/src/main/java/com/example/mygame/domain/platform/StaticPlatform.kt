package com.example.mygame.domain.platform

import android.graphics.Bitmap
import com.example.mygame.domain.Platform

class StaticPlatform(initBitmap: Bitmap, createdX: Float, createdY: Float) : Platform(createdX, createdY) {
    init {
        this.bitmap = initBitmap
    }
}