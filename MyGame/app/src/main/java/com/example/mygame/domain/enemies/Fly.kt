package com.example.mygame.domain.enemies

import android.graphics.Bitmap
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Screen

class Fly(initBitmap: Bitmap,
          createdX: Float,
          createdY: Float,
          private val screen: Screen
) : Enemy(createdX, createdY) {
    init {
        bitmap = initBitmap
    }

    enum class DirectionX(val value: Int) {
        LEFT(0),
        RIGHT(1)
    }

    private var directionX = getDirection()

    override val width: Float
        get() = WIDTH

    override val height: Float
        get() = HEIGHT

    private fun getDirection(): DirectionX {
        val randomValue = (DirectionX.LEFT.value .. DirectionX.RIGHT.value).random()
        return if (randomValue == DirectionX.LEFT.value) {
            DirectionX.LEFT
        } else {
            DirectionX.RIGHT
        }
    }

    private fun changeDirectionX() {
        if (right >= screen.right) {
            directionX = DirectionX.LEFT
        } else if (left <= screen.left) {
            directionX = DirectionX.RIGHT
        }
    }

    override fun updatePosition(elapsedTime: Float) {
        if (directionX == DirectionX.LEFT) {
            x -= GameConstants.FLY_MOVE_SPEED * elapsedTime
        } else {
            x += GameConstants.FLY_MOVE_SPEED * elapsedTime
        }

        changeDirectionX()
    }

    companion object {
        private const val WIDTH = 162f
        private const val HEIGHT = 151f
    }
}