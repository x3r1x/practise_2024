package com.example.mygame.domain.enemy

import android.graphics.Bitmap

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
        const val WIDTH = 275f
        private const val HEIGHT = 174f
    }
}