package com.example.mygame.`object`.platforms

import android.graphics.Bitmap
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Screen

class MovingPlatformOnX(
    initBitmap: Bitmap,
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    private var directionX = directionXRandom()

    init {
        this.bitmap = initBitmap
    }

    enum class DirectionX(val value: Int) {
        LEFT(0),
        RIGHT(1)
    }

    fun changeDirectionX(screen: Screen) {
        if (right >= screen.width) {
            directionX = DirectionX.LEFT
        } else if (left <= 0f) {
            directionX = DirectionX.RIGHT
        }
    }

    private fun directionXRandom() : DirectionX {
        val value = (DirectionX.LEFT.value..DirectionX.RIGHT.value).random()
        if (value == DirectionX.LEFT.value) {
            return DirectionX.LEFT
        } else {
            return DirectionX.RIGHT
        }
    }

    override fun updatePositionX(newX: Float) {
        if (directionX == DirectionX.LEFT) {
            x -= SPEED_ON_X
        } else {
            x += SPEED_ON_X
        }
    }

    companion object {
        private const val SPEED_ON_X = 2f
    }
}