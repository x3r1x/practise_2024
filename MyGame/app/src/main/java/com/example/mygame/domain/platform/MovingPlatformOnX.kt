package com.example.mygame.domain.platform

import android.graphics.Bitmap
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Platform
import com.example.mygame.domain.Screen

class MovingPlatformOnX(
    initBitmap: Bitmap,
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    init {
        this.bitmap = initBitmap
    }

    enum class DirectionX(val value: Int) {
        LEFT(0),
        RIGHT(1)
    }

    private var directionX = directionXRandom()

    fun changeDirectionX(screen: Screen) {
        if (right >= screen.width) {
            directionX = DirectionX.LEFT
        } else if (left <= 0f) {
            directionX = DirectionX.RIGHT
        }
    }

    private fun directionXRandom() : DirectionX {
        val value = (DirectionX.LEFT.value..DirectionX.RIGHT.value).random()

        return if (value == DirectionX.LEFT.value) {
            DirectionX.LEFT
        } else {
            DirectionX.RIGHT
        }
    }

    override fun updatePosition(elapsedTime: Float) {
        if (directionX == DirectionX.LEFT) {
            x -= GameConstants.PLATFORM_ON_X_SPEED * elapsedTime
        } else {
            x += GameConstants.PLATFORM_ON_X_SPEED * elapsedTime
        }
    }
}