package com.example.mygame.`object`.platforms

import android.graphics.Bitmap
import android.util.Log
import com.example.mygame.`object`.Platform

class MovingPlatformOnY(
    initBitmap: Bitmap,
    createdX: Float,
    createdY: Float,
    screenHeight: Float
) : Platform(createdX, createdY) {
    var minY = 0f
    var maxY = 0f

    private var range = 0f
    init {
        this.bitmap = initBitmap
        minY = createdY - screenHeight / 8
        maxY = createdY + screenHeight / 8
        range = screenHeight / 8
    }

    enum class DirectionY(val value: Int) {
        UP(0),
        DOWN(1),
    }

    var directionY = randomDirectionY()

    private fun randomDirectionY() : DirectionY {
        var value = (DirectionY.UP.value..DirectionY.DOWN.value).random()

        if (value == DirectionY.UP.value) {
            return DirectionY.UP
        } else {
            return DirectionY.DOWN
        }
    }

    fun updateDirection() {
        if (y <= minY) {
            directionY = DirectionY.DOWN
        } else if (y + height >= maxY) {
            directionY = DirectionY.UP
        }
    }

    // Проблема при движении платформы вверх и смещении всех платформ - текущая платформа продолжает двигаться вверх и ее границы съезжают
    override fun setPosition(startX: Float, startY: Float) {
        var offsetY = startY - y
        minY += offsetY
        maxY += offsetY
        x = startX
        y = startY
        Log.i("rangeY", "range: [$minY ; $maxY]  y: $y")
    }

    override fun updatePositionY(previousY: Float, elapsedTime: Float) {
        updateDirection()
        if (directionY == DirectionY.UP) {
            y -= speedY
        } else if (directionY == DirectionY.DOWN) {
            y += speedY
        }
    }

    companion object {
        const val speedY = 2f
    }
}