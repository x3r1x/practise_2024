package com.example.mygame.`object`.bonuses

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Player

class Jetpack(private val initDefaultJetpack: Bitmap,
              private val initLeftPlayerJetpack: Bitmap,
              private val initRightPlayerJetpack: Bitmap,
              createdX: Float,
              createdY: Float
) : IDrawable, IMoveable, IGameObject {
    private var isOnPlayer = false

    private var paint = Paint().apply {
        alpha = DEFAULT_TRANSPARENCY
    }

    override var x = createdX
    override var y = createdY

    override var left = x - WIDTH / 2
    override var top = y - WIDTH / 2
    override var bottom = y + WIDTH / 2
    override var right = x + WIDTH / 2

    override var isDisappeared = false

    private lateinit var player: Player

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
        player.speedY = 0f
        isDisappeared = true
    }

    override fun draw(canvas: Canvas) {
        if (isOnPlayer) {
            if (player.directionX == Player.DirectionX.LEFT) {
                canvas.drawBitmap(initLeftPlayerJetpack, player.x - WIDTH / 2 + OFFSET_ON_PLAYER_LEFT, player.y - HEIGHT / 2, paint)
            } else if (player.directionX == Player.DirectionX.RIGHT) {
                canvas.drawBitmap(initRightPlayerJetpack, player.x - WIDTH / 2 - OFFSET_ON_PLAYER_RIGHT, player.y - HEIGHT / 2, paint)
            }
        } else if(!isDisappeared) {
            canvas.drawBitmap(initDefaultJetpack, left, top, null)
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

    companion object {
        private const val WIDTH = 124f
        private const val HEIGHT = 111f

        private const val OFFSET_ON_PLAYER_RIGHT = 95f
        private const val OFFSET_ON_PLAYER_LEFT = 127f

        private const val JETPACK_DURATION : Long = 6000
        private const val JETPACK_TIMER_TICK : Long = 500
        private const val WHEN_TO_PULSE : Long = 2000

        private const val DEFAULT_TRANSPARENCY = 256
        private const val PULSE_TRANSPARENCY = 128

        private const val PLAYER_SPEED_WITH_JETPACK = 800f
    }
}