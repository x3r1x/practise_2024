package com.example.mygame.domain.enemies

import android.graphics.Bitmap
import com.example.mygame.domain.Enemy

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
        private const val WIDTH = 275f
        private const val HEIGHT = 174f

        const val DEATH_OFFSET_X = 60f
    }
}