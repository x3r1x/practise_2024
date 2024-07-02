package com.example.mygame.`object`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import com.example.mygame.Physics
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable

class Player(private val idleImage: Bitmap, private val jumpImage: Bitmap) : IDrawable, ICollidable {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1),
    }

    enum class DirectionX(val value: Int) {
        LEFT(1),
        STILL(0),
        RIGHT(1)
    }

    var directionY = DirectionY.DOWN

    private var speedY = 0f

    private var directionX = DirectionX.STILL

    private var x = 0f
    private var y = 0f

    //?Questionable part?
    var lastCollision: ICollidable? = null
    private var jumpSpeed = 0f
    private var fallBoost = 0f
    private var realJumpHeight = 0f
    private var initialY = 0f
    //??

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    override val left
        get() = x - RADIUS
    override val right
        get() = x + RADIUS
    override val top
        get() = y - RADIUS
    override val bottom
        get() = y + RADIUS

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

    private fun changeDirection(newX: Float, newY: Float) {
        if (x + newX < x) {directionX = DirectionX.LEFT}
        if (x + newX > x) {directionX = DirectionX.RIGHT}
    }

    private fun applyTransformations(matrix: Matrix, destRect: RectF) {
        val scaleX = destRect.width() / idleImage.width
        val scaleY = destRect.height() / idleImage.height

        if (directionX == DirectionX.LEFT) {
            matrix.preScale(-scaleX, scaleY)
            matrix.postTranslate(destRect.right, destRect.top)
        } else {
            matrix.postScale(scaleX, scaleY)
            matrix.postTranslate(destRect.left, destRect.top)
        }
    }

    private fun selectImage(): Bitmap {
        return if (directionY == DirectionY.UP) {
            jumpImage
        } else {
            idleImage
        }
    }

    override fun draw(canvas: Canvas) {
        val matrix = Matrix()
        val destRect = RectF(left, top, right, bottom)

        applyTransformations(matrix, destRect)

        val imageToDraw = selectImage()
        canvas.drawBitmap(imageToDraw, matrix, null)

        canvas.drawRect(left, top, right, bottom, borderPaint)
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
        initialY = startY
    }

    override fun updatePosition(newX: Float, newY: Float) {
        changeDirection(newX, newY)
        x += newX
        y += speedY * directionY.value
        updateSpeedY()
    }

    override fun onObjectCollide() {
        if (directionY == DirectionY.DOWN) {
            directionY = DirectionY.UP
            initialY = y
        }
    }

    override fun onScreenCollide(screen: Screen) {
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
//          y = screen.bottom - bottom
            directionY = DirectionY.UP
            initialY = y
        }
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        //Проверка на состояние шара. Если DOWN - обработка коллизии, иначе нет
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
        private const val START_JUMP_SPEED = 50f
        private const val RADIUS = 100f
        private const val MAX_JUMP_HEIGHT = 600f
    }
}