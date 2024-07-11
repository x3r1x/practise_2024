package com.example.mygame.`object`

import android.graphics.RectF
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import com.example.mygame.Physics
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IGameObject

class Player(private val idleImage: Bitmap,
             private val jumpImage: Bitmap,
             private val deadImage: Bitmap
) : IDrawable, IMoveable, IGameObject {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1),
    }

    enum class DirectionX {
        LEFT,
        RIGHT
    }

    var isWithJetpack = false

    var isWithShield = false

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

    var directionX = DirectionX.RIGHT

    var directionY = DirectionY.DOWN

    var speedY = 0f

    override var isDisappeared = false
    var isDead = false

    fun jump(jumpSpeed: Float = JUMP_SPEED) {
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

    private fun changeDirectionX(newX: Float) {
        if (newX < -DISTANCE_TO_TURN) {
            directionX = DirectionX.LEFT
        } else if (newX > DISTANCE_TO_TURN) {
            directionX = DirectionX.RIGHT
        }
    }

    private fun applyTransformations(matrix: Matrix, destRect: RectF, image: Bitmap) {
        val scaleX = destRect.width() / image.width
        val scaleY = destRect.height() / image.height

        if (directionX == DirectionX.LEFT) {
            matrix.preScale(-scaleX, scaleY)
            matrix.postTranslate(destRect.right, destRect.top)
        } else {
            matrix.postScale(scaleX, scaleY)
            matrix.postTranslate(destRect.left, destRect.top)
        }
    }

    private fun selectImage(): Bitmap {
        return if (isDead) {
            deadImage
        } else if (directionY == DirectionY.UP) {
            jumpImage
        } else {
            idleImage
        }
    }

    private fun applyRect() : RectF {
        return if (isDead) {
            RectF(x - DEAD_WIDTH / 2, y - DEAD_HEIGHT / 2, x + DEAD_WIDTH / 2, y + DEAD_HEIGHT / 2)
        } else {
            RectF(left, top, right, bottom)
        }
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun draw(canvas: Canvas) {
        val matrix = Matrix()
        val destRect = applyRect()
        val imageToDraw = selectImage()

        applyTransformations(matrix, destRect, imageToDraw)

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

        if (!isWithJetpack) {
            speedY += elapsedTime * Physics.GRAVITY * directionY.value

            if (y >= previousY && directionY == DirectionY.UP) {
                directionY = DirectionY.DOWN
            }
        }
    }

    companion object {
        const val SPRING_JUMP_SPEED = 2200f

        private const val DISTANCE_TO_TURN = 1f
        private const val RADIUS = 75f
        private const val JUMP_SPEED = 1850f

        private const val DEAD_WIDTH = 120f
        private const val DEAD_HEIGHT = 190f
    }
}