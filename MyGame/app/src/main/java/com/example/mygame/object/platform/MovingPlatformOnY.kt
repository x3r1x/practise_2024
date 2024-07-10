package com.example.mygame.`object`.platform

import android.graphics.Bitmap
import com.example.mygame.`object`.Platform

class MovingPlatformOnY(
    initBitmap: Bitmap,
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    private var minY = 0f
    private var maxY = 0f

    init {
        this.bitmap = initBitmap
        minY = createdY - RANGE
        maxY = createdY + RANGE
    }

    enum class DirectionY(val value: Int) {
        UP(0),
        DOWN(1),
    }

    private var directionY = randomDirectionY()

    private fun randomDirectionY() : DirectionY {
        val value = (DirectionY.UP.value..DirectionY.DOWN.value).random()

        if (value == DirectionY.UP.value) {
            return DirectionY.UP
        } else {
            return DirectionY.DOWN
        }
    }

    private fun updateDirection() {
        if (y <= minY) {
            directionY = DirectionY.DOWN
        } else if (y + height >= maxY) {
            directionY = DirectionY.UP
        }
    }

    override fun setPosition(startX: Float, startY: Float) {
        val offsetY = startY - y

        minY += offsetY
        maxY += offsetY

        x = startX
        y = startY
    }

    override fun updatePositionY(elapsedTime: Float) {
        updateDirection()

        if (directionY == DirectionY.UP) {
            y -= SPEED_ON_Y
        } else if (directionY == DirectionY.DOWN) {
            y += SPEED_ON_Y
        }
    }

    companion object {
        const val SPEED_ON_Y = 2f
        const val RANGE = 400f
    }
}