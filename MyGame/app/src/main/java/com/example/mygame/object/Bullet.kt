package com.example.mygame.`object`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IVisitor
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class Bullet(private val image: Bitmap) : IMoveable, IDrawable, IGameObject {

    override var x = 0f
    override var y = 0f
    override var isDisappeared = false

    override val left: Float
        get() = x - WIDTH / 2
    override val right: Float
        get() = x + WIDTH / 2
    override val top: Float
        get() = y - HEIGHT / 2
    override val bottom: Float
        get() = y + HEIGHT / 2

    private var velocityX = 0f
    private var velocityY = 0f

    fun shoot(startX: Float, startY: Float, targetX: Float, targetY: Float) {
        setPosition(startX, startY)

        val angle = calculateAngle(startX, startY, targetX, targetY)
        velocityX = (SPEED * cos(angle)).toFloat()
        velocityY = (SPEED * sin(angle)).toFloat()
    }

    private fun calculateAngle(startX: Float, startY: Float, targetX: Float, targetY: Float): Double {
        val deltaX = targetX - startX
        val deltaY = targetY - startY
        return atan2(deltaY.toDouble(), deltaX.toDouble())
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun updatePositionX(elapsedTime: Float) {
        x += velocityX * elapsedTime
    }

    override fun updatePositionY(elapsedTime: Float) {
        y += velocityY * elapsedTime
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun draw(canvas: Canvas) {
        val matrix = Matrix()
        matrix.postTranslate(left, top)
        canvas.drawBitmap(image, matrix, null)
    }

    companion object {
        private const val WIDTH = 20f
        private const val HEIGHT = 20f
        private const val SPEED = 500f // Скорость пули в пикселях в секунду
    }
}
