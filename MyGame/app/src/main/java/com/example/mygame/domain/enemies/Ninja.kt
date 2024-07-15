package com.example.mygame.domain.enemies

import android.graphics.Bitmap
import com.example.mygame.domain.Enemy
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Screen
import kotlin.random.Random

class Ninja(initBimap: Bitmap,
            createdX: Float,
            createdY: Float,
            private val screen: Screen
) : Enemy(createdX, createdY) {
    init {
        bitmap = initBimap
    }

    enum class DirectionX(val value: Int) {
        LEFT(0),
        RIGHT(1)
    }

    private var directionX = getDirectionX()
    private var speedX = GameConstants.NINJA_START_SPEED_X
    private var convergence = getRandomConvergence()

    override val width: Float
        get() = WIDTH

    override val height: Float
        get() = HEIGHT

    private fun getDirectionX() : DirectionX {
        val randomValue = (DirectionX.LEFT.value .. DirectionX.RIGHT.value).random()
        return if (randomValue == DirectionX.LEFT.value) {
            DirectionX.LEFT
        } else {
            DirectionX.RIGHT
        }
    }

    private fun updateStats() {
        convergence -= speedX

        if (convergence <= 0f) {
            changeDirectionX()
            convergence = getRandomConvergence()
        }

        setRandomSpeed()
    }

    private fun changeDirectionX() {
        directionX = if (directionX == DirectionX.LEFT) {
            DirectionX.RIGHT
        } else {
            DirectionX.LEFT
        }
    }

    private fun getRandomConvergence(): Float {
        if (directionX == DirectionX.LEFT && left < GameConstants.NINJA_MAX_CONVERGENCE) {
            if (left < GameConstants.NINJA_MIN_CONVERGENCE) {
                changeDirectionX()
            } else {
                return (GameConstants.NINJA_MIN_CONVERGENCE.toInt()..left.toInt()).random()
                    .toFloat()
            }
        } else if (directionX == DirectionX.RIGHT && screen.right - right < GameConstants.NINJA_MAX_CONVERGENCE) {
            if (screen.right - right < GameConstants.NINJA_MIN_CONVERGENCE) {
                changeDirectionX()
            } else {
                return (GameConstants.NINJA_MIN_CONVERGENCE.toInt()..(screen.right - right).toInt()).random()
                    .toFloat()
            }
        }

        return (GameConstants.NINJA_MIN_CONVERGENCE.toInt() .. GameConstants.NINJA_MAX_CONVERGENCE.toInt()).random().toFloat()
    }

    private fun setRandomSpeed() {
        val random = Random.nextFloat()

        if (random < 0.05f) {
            speedX = (GameConstants.NINJA_MIN_SPEED_X .. GameConstants.NINJA_MAX_SPEED_X).random().toFloat()
        }
    }

    override fun updatePositionX(newX: Float) {
        if (directionX == DirectionX.LEFT) {
            x -= speedX
        } else {
            x += speedX
        }

        updateStats()
    }

    companion object {
        private const val WIDTH = 160f
        private const val HEIGHT = 125f
    }
}