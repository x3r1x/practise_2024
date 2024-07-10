package com.example.mygame.`object`.platform

import android.graphics.Bitmap
import com.example.mygame.`object`.Platform

class StaticPlatform(initBitmap: Bitmap, createdX: Float, createdY: Float) : Platform(createdX, createdY) {
    init {
        this.bitmap = initBitmap
    }
}