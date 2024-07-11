package com.example.mygame.`object`.enemies

import android.graphics.Bitmap
import com.example.mygame.`object`.Enemy

class Bully(initBitmap: Bitmap,
            createdX: Float,
            createdY: Float
) : Enemy(createdX, createdY) {
    init {
        bitmap = initBitmap
    }

    override val width: Float
        get() = WIDTH

    override val height: Float
        get() = HEIGHT

    companion object {
        private const val WIDTH = 361f
        private const val HEIGHT = 228f
    }
}