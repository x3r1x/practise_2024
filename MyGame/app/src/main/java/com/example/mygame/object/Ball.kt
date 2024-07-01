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

    private var x = 0f
    private var y = 0f

    private var initialY = 0f

    private val jumpSpeed = Physics(0f).getStartCollisionSpeed(MAX_JUMP_HEIGHT, FALL_BOOST)

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

    private val speedX = 2.5f

    private fun updateSpeedY() {
        when (directionY) {
            DirectionY.UP -> {
                speedY -= FALL_BOOST
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
        x += newX * speedX
        y += speedY * directionY.value
        updateSpeedY()
    }

    override fun behaviour() {
        if (directionY == DirectionY.DOWN) {
            directionY = DirectionY.UP
            speedY = jumpSpeed
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
            y = 0f + RADIUS
            initialY = y
        }
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        other ?: return false

        return !(right <= other.left ||
                left >= other.right ||
                bottom <= other.top ||
                top >= other.bottom)
    }

    companion object {
        const val MAX_JUMP_HEIGHT = 600f
        const val JUMP_TIME = 50f
        private const val FALL_BOOST = 0.5f
        private const val RADIUS = 50f
    }
}