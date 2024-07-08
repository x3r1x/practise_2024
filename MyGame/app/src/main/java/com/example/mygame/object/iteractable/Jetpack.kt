package com.example.mygame.`object`.iteractable

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.R
import com.example.mygame.`interface`.IBonus
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen

class Jetpack(resources: Resources, entity: Player) : IDrawable, ICollidable, IMoveable, IBonus {
    private val res = resources
    private val player = entity

    private var bitmap = BitmapFactory.decodeResource(res, IMAGE, BITMAP_OPTIONS)
    private var paint = Paint().apply {
        alpha = DEFAULT_TRANSPARENCY
    }

    private var isGone = false

    override var x = 0f
    override var y = 0f

    override var left = 0f
    override var top = 0f
    override var bottom = 0f
    override var right = 0f

    override val isPassable = true

    override var isInSpring: Boolean? = null

    fun createOnPlatform(platform: Platform) {
        setPosition(platform.x, platform.top - HEIGHT / 2 - OFFSET)
    }

    private fun movePlayer(player: Player) {
        player.directionY = Player.DirectionY.UP
        player.speedY = PLAYER_SPEED_WITH_JETPACK
    }

    private fun startDisappearingTimer() {
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

    //FIXME: The problem with that is that it doesn't really dispose. It just goes invisible and
    //FIXME: teleports to the up left of the screen.
    private fun dispose() {
        player.isWithJetpack = false
        player.directionY = Player.DirectionY.DOWN
        bitmap = BitmapFactory.decodeResource(res, IMAGE, BITMAP_OPTIONS)
        player.speedY = 0f
        isGone = true
        left = 0f
        right = 0f
        top = 0f
        bottom = 0f
    }

    override fun draw(canvas: Canvas) {
        if (player.isWithJetpack) {
            if (player.directionX == Player.DirectionX.LEFT) {
                bitmap = BitmapFactory.decodeResource(res, IMAGE_ON_PLAYER_LEFT, BITMAP_OPTIONS)
                canvas.drawBitmap(bitmap, player.x - WIDTH / 2 + OFFSET_ON_PLAYER_LEFT, player.y - HEIGHT / 2, paint)
            } else if (player.directionX == Player.DirectionX.RIGHT) {
                bitmap = BitmapFactory.decodeResource(res, IMAGE_ON_PLAYER_RIGHT, BITMAP_OPTIONS)
                canvas.drawBitmap(bitmap, player.x - WIDTH / 2 - OFFSET_ON_PLAYER_RIGHT, player.y - HEIGHT / 2, paint)
            }
        } else if(!isGone) {
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

    override fun onScreenCollide(screen: Screen) {}

    override fun onObjectCollide(obj: ICollidable) {
        player.isWithJetpack = true
        startDisappearingTimer()
        movePlayer(player)
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        return if (other !is Player) {
            false
        } else {
            (other.bottom <= top && (other.left <= right || other.right >= left)
                    && other.bottom >= bottom)
        }
    }

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