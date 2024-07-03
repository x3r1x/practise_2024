package com.example.mygame.`object`.platforms

import android.graphics.Bitmap
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Screen

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

    var directionX = directionXRandom()

    private fun directionXRandom() : DirectionX {
        var value = (DirectionX.LEFT.value..DirectionX.RIGHT.value).random()
        if (value == DirectionX.LEFT.value) {
            return DirectionX.LEFT
        } else {
            return DirectionX.RIGHT
        }
    }

    override fun onScreenCollide(screen: Screen) {
        if (left + width >= screen.width) {
            directionX = DirectionX.LEFT
        } else if (left <= 0f) {
            directionX = DirectionX.RIGHT
        }
    }

    override fun updatePositionX(newX: Float) {
        if (directionX == DirectionX.LEFT) {
            x -= speedX
        } else {
            x += speedX
        }
    }

    companion object {
        const val speedX = 2f
    }
}