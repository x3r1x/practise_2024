package com.example.mygame.`object`

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.os.CountDownTimer
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IVisitor

open class Enemy(createdX: Float, createdY: Float) : IDrawable, IMoveable, IGameObject {
    open val width = 0f
    open val height = 0f

    private var isDead = false

    override var isDisappeared = false

    override var x = createdX
    override var y = createdY

    override val left: Float
        get() = x - width / 2

    override val bottom: Float
        get() = y + height / 2

    override val right: Float
        get() = x + width / 2

    override val top: Float
        get() = y - height / 2

    protected lateinit var bitmap: Bitmap

    fun killPlayer(player: Player) {
        if (!isDead) {
            player.directionY = Player.DirectionY.DOWN
            player.speedY = 0f
            player.isDead = true
        }
    }

    fun killEnemy() {
        isDead = true
        runDeathAnimation()
    }

    private fun runDeathAnimation() {
        val timer = object : CountDownTimer(1000, 1) {
            override fun onTick(p0: Long) {
                setPosition(x, y + DEATH_OFFSET_PER_FRAME)
            }

            override fun onFinish() {}
        }

        timer.start()
    }

    private fun applyTransformations(matrix: Matrix, destRect: RectF) {
        val scaleX = destRect.width() / bitmap.width
        val scaleY = destRect.height() / bitmap.height

        matrix.preScale(scaleX, scaleY)
        matrix.postTranslate(destRect.left, destRect.top)
    }

    override fun draw(canvas: Canvas) {
        val matrix = Matrix()
        val destRect = RectF(left, top, right, bottom)

        applyTransformations(matrix, destRect)

        val imageToDraw = bitmap
        canvas.drawBitmap(imageToDraw, matrix, null)
    }

    override fun updatePositionX(newX: Float) {}
    override fun updatePositionY(elapsedTime: Float) {}

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    companion object {
        private const val DEATH_OFFSET_PER_FRAME = 1f
    }
}