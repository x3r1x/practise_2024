package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.mygame.Physics
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import kotlin.math.log

class Ball : IDrawable, ICollidable {
    enum class DirectionY(val value: Int) {
        UP(1),
        DOWN(-1),
    }

    var directionY = DirectionY.DOWN

    private var speedY = 0f

    override var x = 0f
    override var y = 0f

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

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, RADIUS, ballPaint)
        canvas.drawCircle(x, y, RADIUS, borderPaint)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePositionX(newX: Float) {
        x += newX * SPEED_X_MULTIPLIER
    }

    override fun updatePositionY(newY: Float, elapsedTime: Float) {
        y += speedY * directionY.value * elapsedTime
        speedY += elapsedTime * Physics.GRAVITY * directionY.value

        if (speedY <= 0f && directionY == DirectionY.UP) {
            directionY = DirectionY.DOWN
        }

        Log.d("", "Speed: $speedY")
        Log.d("", "Direction: $directionY")
    }

    override fun behaviour(platformTop: Float) {
        setPosition(x, platformTop - RADIUS - 10f)
        directionY = DirectionY.UP
        speedY = JUMP_SPEED
//        Log.d("", "We are in behaviour!")
    }

    override fun screenBehaviour(screen: Screen) {
        if (left < screen.left) {
            x = screen.width - RADIUS
        }

        if (right > screen.right) {
            x = 0f + RADIUS
        }

//        if (top < screen.top) {
//            y = RADIUS
//            directionY = DirectionY.DOWN
//        }
//
//        if (bottom > screen.bottom) {
////            y = screen.bottom - bottom
//            directionY = DirectionY.UP
//        }
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        other ?: return false

        val isIntersect = !(right < other.left ||
                left > other.right ||
                bottom < other.top ||
                top > other.bottom)

        if (isIntersect) {
            Log.d("", "Direction: ${directionY}")
        }

        return isIntersect && directionY == DirectionY.DOWN
    }

    companion object {
        private const val RADIUS = 50f
        private const val JUMP_SPEED = 300f
        private const val SPEED_X_MULTIPLIER = 2.5f
    }
}