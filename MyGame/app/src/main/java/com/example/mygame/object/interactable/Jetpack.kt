package com.example.mygame.`object`.interactable

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.R
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player

class Jetpack(private val resources: Resources) : IDrawable, IMoveable, IGameObject {
    private var isOnPlayer = false

    private var bitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTIONS)
    private var paint = Paint().apply {
        alpha = DEFAULT_TRANSPARENCY
    }

    override var x = 0f
    override var y = 0f

    override var left = 0f
    override var top = 0f
    override var bottom = 0f
    override var right = 0f

    override var isDisappeared = false

    private lateinit var player: Player

    fun createOnPlatform(platform: Platform) {
        setPosition(platform.x, platform.top - HEIGHT / 2 - OFFSET)
    }

    fun initPlayer(entity: Player) {
        player = entity
        player.isWithJetpack = true
        isOnPlayer = true
    }

    fun fly() {
        player.directionY = Player.DirectionY.UP
        player.speedY = PLAYER_SPEED_WITH_JETPACK
    }

    fun startDisappearingTimer() {
        val timer = object : CountDownTimer(JETPACK_DURATION, JETPACK_TIMER_TICK) {
            override fun onTick(p0: Long) {
                if (p0 <= WHEN_TO_PULSE) {
                    paint.alpha = if (paint.alpha == PULSE_TRANSPARENCY) {
                        DEFAULT_TRANSPARENCY
                    } else {
                        PULSE_TRANSPARENCY
                    }
                }
            }

            override fun onFinish() {
                dispose()
            }
        }

        timer.start()
    }

    private fun dispose() {
        player.isWithJetpack = false
        isOnPlayer = false
        player.directionY = Player.DirectionY.DOWN
        bitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTIONS)
        player.speedY = 0f
        isDisappeared = true
    }

    override fun draw(canvas: Canvas) {
        if (isOnPlayer) {
            if (player.directionX == Player.DirectionX.LEFT) {
                bitmap = BitmapFactory.decodeResource(resources, IMAGE_ON_PLAYER_LEFT, BITMAP_OPTIONS)
                canvas.drawBitmap(bitmap, player.x - WIDTH / 2 + OFFSET_ON_PLAYER_LEFT, player.y - HEIGHT / 2, paint)
            } else if (player.directionX == Player.DirectionX.RIGHT) {
                bitmap = BitmapFactory.decodeResource(resources, IMAGE_ON_PLAYER_RIGHT, BITMAP_OPTIONS)
                canvas.drawBitmap(bitmap, player.x - WIDTH / 2 - OFFSET_ON_PLAYER_RIGHT, player.y - HEIGHT / 2, paint)
            }
        } else if(!isDisappeared) {
            canvas.drawBitmap(bitmap, left, top, null)
        }
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
        left = x - WIDTH / 2
        right = x + WIDTH / 2
        top = y - HEIGHT / 2
        bottom = y + HEIGHT / 2
    }

    override fun updatePositionX(newX: Float) {}
    override fun updatePositionY(elapsedTime: Float) {}

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

//    override fun collidesWith(other: ICollidable?): Boolean {
//        return if (other !is Player) {
//            false
//        } else {
//            (other.bottom <= top && (other.left <= right || other.right >= left)
//                    && other.bottom >= bottom)
//        }
//    }

    companion object {
        private const val WIDTH = 124f
        private const val HEIGHT = 111f
        private const val OFFSET = 20f

        private const val OFFSET_ON_PLAYER_RIGHT = 95f
        private const val OFFSET_ON_PLAYER_LEFT = 127f

        private const val JETPACK_DURATION : Long = 6000
        private const val JETPACK_TIMER_TICK : Long = 500
        private const val WHEN_TO_PULSE : Long = 2000

        private const val DEFAULT_TRANSPARENCY = 256
        private const val PULSE_TRANSPARENCY = 128

        private const val PLAYER_SPEED_WITH_JETPACK = 800f

        private val IMAGE = R.drawable.jetpack
        private val IMAGE_ON_PLAYER_LEFT = R.drawable.player_jetpack_left
        private val IMAGE_ON_PLAYER_RIGHT = R.drawable.player_jetpack_right

        private val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}