package com.example.mygame.`object`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.Physics
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IVisitor

class Player(private val idleImage: Bitmap, private val jumpImage: Bitmap) : IDrawable, IMoveable, IGameObject {
    private var directionX = DirectionX.RIGHT
    private var directionY = DirectionY.DOWN

    private var speedY = 0f

    override var isDisappeared = false

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

    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1),
    }

    enum class DirectionX() {
        LEFT,
        RIGHT
    }

    fun getDirectionX() :DirectionX {
        return directionX
    }

    fun getDirectionY() :DirectionY {
        return directionY
    }

    fun repulsion() {
        directionY = DirectionY.UP
        speedY = JUMP_SPEED
    }

    fun movingThroughScreen(screen: Screen) {
        if (x < screen.left) {
            x = screen.width - RADIUS
        }

        if (x > screen.right) {
            x = 0f + RADIUS
        }
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    private fun changeDirectionX(newX: Float) {
        if (newX < -DISTANCE_TO_TURN)
        {
            directionX = DirectionX.LEFT
        } else if (newX > DISTANCE_TO_TURN)
        {
            directionX = DirectionX.RIGHT
        }
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
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePositionX(newX: Float) {
        x += newX
        changeDirectionX(newX)
    }

    override fun updatePositionY(elapsedTime: Float) {
        val previousY = y

        y += speedY * directionY.value * elapsedTime
        speedY += elapsedTime * Physics.GRAVITY * directionY.value

        if (y >= previousY && directionY == DirectionY.UP) {
            directionY = DirectionY.DOWN
        }
    }

    companion object {
        private const val DISTANCE_TO_TURN = 1f
        private const val RADIUS = 75f
        private const val JUMP_SPEED = 1000f //920f
    }
}