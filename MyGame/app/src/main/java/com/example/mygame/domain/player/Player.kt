package com.example.mygame.domain.player

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Handler
import android.os.Looper
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.Screen

class Player(private val idleImage: Bitmap,
            private val jumpImage: Bitmap,
            private val deadImage: Bitmap,
            private val shootImage: Bitmap
) : IDrawable, IMoveable, IGameObject {
    enum class DirectionY(val value: Int) {
        UP(-1),
        DOWN(1),
    }

    enum class DirectionX(val value: Int) {
        LEFT(-1),
        RIGHT(1)
    }

    var isWithJetpack = false
    var isWithShield = false
    var isShooting = false

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

    private var shootingRunnable: Runnable? = null
    private val shootingHandler = Handler(Looper.getMainLooper())

    override var isDisappeared = false
    var isDead = false

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
        x += deltaX * elapsedTime
        changeDirectionX(deltaX)
    }

    private fun changeDirectionX(newX: Float) {
        if (newX < - GameConstants.PLAYER_DISTANCE_TO_TURN) {
            directionX = DirectionX.LEFT
        } else if (newX > GameConstants.PLAYER_DISTANCE_TO_TURN) {
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
        } else if (isShooting) {
            shootImage
        } else if (directionY == DirectionY.UP) {
            jumpImage
        } else {
            idleImage
        }
    }

    private fun applyRect() : RectF {
        return if (isDead) {
            RectF(x - DEAD_WIDTH / 2, y - DEAD_HEIGHT / 2, x + DEAD_WIDTH / 2, y + DEAD_HEIGHT / 2)
        } else if (isShooting) {
            RectF(x - SHOOTING_WIDTH / 2, y - SHOOTING_HEIGHT / 2, x + SHOOTING_WIDTH / 2, y + SHOOTING_HEIGHT / 2)
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

    private val positionChangedListeners = mutableListOf<OnPositionChangedListener>()

    fun addOnPositionChangedListener(listener: OnPositionChangedListener) {
        positionChangedListeners.add(listener)
    }

    fun removeOnPositionChangedListener(listener: OnPositionChangedListener) {
        positionChangedListeners.remove(listener)
    }

    fun notifyPositionChanged() {
        positionChangedListeners.forEach { it.onPositionChanged(this) }
    }

    // Other properties and methods

    interface OnPositionChangedListener {
        fun onPositionChanged(player: Player)
    }

    companion object {
        const val SHOOTING_WIDTH = 105f
        const val SHOOTING_HEIGHT = 200f

        private const val RADIUS = 75f

        private const val DEAD_WIDTH = 120f
        private const val DEAD_HEIGHT = 190f
    }
}