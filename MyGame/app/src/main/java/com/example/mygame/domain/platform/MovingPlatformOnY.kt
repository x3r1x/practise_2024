package com.example.mygame.domain.platform

import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Platform

class MovingPlatformOnY(
    createdX: Float,
    createdY: Float
) : Platform(createdX, createdY) {
    private var minY = 0f
    private var maxY = 0f

    init {
        minY = createdY - GameConstants.PLATFORM_ON_Y_RANGE
        maxY = createdY + GameConstants.PLATFORM_ON_Y_RANGE
    }

    enum class DirectionY(val value: Int) {
        UP(0),
        DOWN(1),
    }

    private var directionY = randomDirectionY()

    private fun randomDirectionY() : DirectionY {
        val value = (DirectionY.UP.value..DirectionY.DOWN.value).random()

        return if (value == DirectionY.UP.value) {
            DirectionY.UP
        } else {
            DirectionY.DOWN
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

    override fun updatePosition(elapsedTime: Float) {
        updateDirection()

        if (directionY == DirectionY.UP) {
            y -= GameConstants.PLATFORM_ON_Y_SPEED * elapsedTime
        } else if (directionY == DirectionY.DOWN) {
            y += GameConstants.PLATFORM_ON_Y_SPEED * elapsedTime
        }
    }
}