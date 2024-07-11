package com.example.mygame.`object`.enemies

import android.graphics.Bitmap
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Screen
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
    private var speedX = 4f
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
        if (directionX == DirectionX.LEFT && left < MAX_COVERGENCE) {
            return (MIN_COVERGENCE.toInt() .. left.toInt()).random().toFloat()
        } else if (directionX == DirectionX.RIGHT && screen.right - right < MAX_COVERGENCE) {
            return (MIN_COVERGENCE.toInt() .. (screen.right - right).toInt()).random().toFloat()
        }

        return (MIN_COVERGENCE.toInt() .. MAX_COVERGENCE.toInt()).random().toFloat()
    }

    private fun setRandomSpeed() {
        val random = Random.nextFloat()

        if (random < 0.05f) {
            speedX = (MIN_SPEED_X .. MAX_SPEED_X).random().toFloat()
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

        private const val MIN_SPEED_X = 2
        private const val MAX_SPEED_X = 10

        private const val MIN_COVERGENCE = 10f
        private const val MAX_COVERGENCE = 600f
    }
}