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
import com.example.mygame.`object`.platforms.BreakingPlatform

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

    var directionX = DirectionX.STILL

    var directionY = DirectionY.DOWN

    private var speedY = 0f

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

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


    private fun changeDirection(newX: Float) {
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
    }

    override fun updatePositionX(newX: Float) {
        changeDirection(newX)
        x += newX
    }

    override fun updatePositionY(elapsedTime: Float) {
        var previousY = y

        y += speedY * directionY.value * elapsedTime
        speedY += elapsedTime * Physics.GRAVITY * directionY.value

        if (y >= previousY && directionY == DirectionY.UP) {
            directionY = DirectionY.DOWN
        }
    }

    override fun onObjectCollide(obj: ICollidable) {
        setPosition(x, obj.top - RADIUS - 10f)
        directionY = DirectionY.UP
        speedY = JUMP_SPEED
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

//        if (bottom > screen.bottom) {
//            y = screen.bottom - bottom
//            directionY = DirectionY.UP
//        }
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        //Проверка на состояние шара. Если DOWN - обработка коллизии, иначе нет
        //Сделать с помощью метода intersects() у класса Rect
        other ?: return false

        val isIntersect = !(right < other.left ||
                left > other.right ||
                bottom < other.top ||
                top > other.bottom)

        return isIntersect && directionY == DirectionY.DOWN
    }

    companion object {
        private const val RADIUS = 60f
        private const val JUMP_SPEED = 920f
    }
}