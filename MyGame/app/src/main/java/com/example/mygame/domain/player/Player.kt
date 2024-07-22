package com.example.mygame.domain.player

import android.os.Handler
import android.os.Looper
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.Screen

class Player : IMoveable, IGameObject {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1),
    }

    enum class DirectionX(val value: Int) {
        LEFT(-1),
        RIGHT(1),
    }

    var bonuses = PlayerSelectedBonuses(this)

    var isWithJetpack = false
    var isWithShield = false
    var isDead = false
    var isShooting = false

    var directionX = DirectionX.RIGHT
    var directionY = DirectionY.DOWN

    var speedY = 0f

    private var shootingRunnable: Runnable? = null
    private val shootingHandler = Handler(Looper.getMainLooper())

    override var x = 0f
    override var y = 0f

    override var isDisappeared = false

    override val left
        get() = x - RADIUS
    override val right
        get() = x + RADIUS
    override val top
        get() = y - RADIUS
    override val bottom
        get() = y + RADIUS

    fun jump(jumpSpeed: Float = GameConstants.PLAYER_JUMP_SPEED) {
        directionY = DirectionY.UP

        speedY = jumpSpeed
    }

    fun movingThroughScreen(screen: Screen) {
        if (x < screen.left) {
            x = screen.width - RADIUS
        }

        if (x > screen.right) {
            x = 0f + RADIUS
        }
    }

    fun shoot() {
        isShooting = true

        shootingRunnable?.let {
            shootingHandler.removeCallbacks(it)
        }

        shootingRunnable = Runnable {
            isShooting = false
        }
        shootingHandler.postDelayed(shootingRunnable!!, 300)
    }

    fun updatePositionX(deltaX: Float, elapsedTime: Float) {
        x += deltaX * elapsedTime * GameConstants.PLAYER_ON_X_MULTIPLIER
        changeDirectionX(deltaX)
    }

    private fun changeDirectionX(newX: Float) {
        if (newX < -GameConstants.PLAYER_DISTANCE_TO_TURN) {
            directionX = DirectionX.LEFT
        } else if (newX > GameConstants.PLAYER_DISTANCE_TO_TURN) {
            directionX = DirectionX.RIGHT
        }
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePosition(elapsedTime: Float) {
        val previousY = y

        y += speedY * directionY.value * elapsedTime

        if (!isWithJetpack) {
            speedY += elapsedTime * GameConstants.GRAVITY * directionY.value

            if (y >= previousY && directionY == DirectionY.UP) {
                directionY = DirectionY.DOWN
            }
        }
    }

    companion object {
        const val SHOOTING_WIDTH = 105f
        const val SHOOTING_HEIGHT = 200f

        private const val RADIUS = 75f
    }
}