package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.Physics
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable

class Ball : IDrawable, ICollidable {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1),
    }

    var speedY = Physics.GRAVITY

    var directionY = DirectionY.DOWN

    var lastCollision: ICollidable? = null

    private var x = 0f
    private var y = 0f

    private var jumpSpeed = 0f
    private var fallBoost = 0f
    private var realJumpHeight = 0f

    private var initialY = 0f

    override val left
        get() = x - RADIUS
    override val right
        get() = x + RADIUS
    override val top
        get() = y - RADIUS
    override val bottom
        get() = y + RADIUS

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    fun updateJumpHeight(screenHeight: Float) {
        realJumpHeight = if (Physics.MAX_JUMP_PIXELS_FROM_BOTTOM - (screenHeight - y) > MAX_JUMP_HEIGHT) {
            MAX_JUMP_HEIGHT
        } else {
            Physics.MAX_JUMP_PIXELS_FROM_BOTTOM - (screenHeight - y)
        }
    }

    fun updateSpeedAndBoost() {
        jumpSpeed = Physics(0f).getStartCollisionSpeed(realJumpHeight, JUMP_TIME)
        fallBoost = Physics(0f).getJumpBoost(jumpSpeed, JUMP_TIME)
        speedY = jumpSpeed
    }

    private fun updateSpeedY() {
        when (directionY) {
            DirectionY.UP -> {
                speedY -= fallBoost
                if (initialY - y >= MAX_JUMP_HEIGHT) {
                    directionY = DirectionY.DOWN
                    speedY = Physics.GRAVITY
                }
            }
            DirectionY.DOWN -> {
                speedY += Physics.GRAVITY * Physics.GRAVITY_RATIO
            }
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, RADIUS, ballPaint)
        canvas.drawCircle(x, y, RADIUS, borderPaint)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
        initialY = startY
    }

    override fun updatePosition(newX: Float, newY: Float) {
        x += newX * SPEED_X_MULTIPLIER
        y += speedY * directionY.value
        updateSpeedY()
    }

    override fun behaviour() {
        if (directionY == DirectionY.DOWN) {
            directionY = DirectionY.UP
            initialY = y
        }
    }

    override fun screenBehaviour(screen: Screen) {
        if (left < screen.left) {
            x = screen.width - RADIUS
        }

        if (right > screen.right) {
            x = 0f + RADIUS
        }

        if (top < screen.top) {
            y = RADIUS
            directionY = DirectionY.DOWN
        }

        if (bottom > screen.bottom) {
//            y = screen.bottom - bottom
            directionY = DirectionY.UP
            initialY = y
        }
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        other ?: return false

        if (!(right <= other.left  ||
              left >= other.right  ||
              bottom <= other.top  ||
              top >= other.bottom)) {
            lastCollision = other
            return true
        }
        return false
    }

    companion object {
        const val JUMP_TIME = 50f
        private const val RADIUS = 50f
        private const val MAX_JUMP_HEIGHT = 600f
        private const val SPEED_X_MULTIPLIER = 2.5f
    }
}