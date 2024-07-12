package com.example.mygame.domain.bonuses

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.player.Player

class Jetpack(private val initDefaultJetpack: Bitmap,
              private val initLeftPlayerJetpack: Bitmap,
              private val initRightPlayerJetpack: Bitmap,
              createdX: Float,
              createdY: Float
) : IDrawable, IMoveable, IBonus, IGameObject {
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

    private var player: Player? = null

    fun initPlayer(entity: Player) {
        player = entity
        player?.isWithJetpack = true
    }

    fun fly() {
        player?.directionY = Player.DirectionY.UP
        player?.speedY = GameConstants.PLAYER_SPEED_WITH_JETPACK
    }

    fun startDisappearingTimer() {
        val timer = object : CountDownTimer(GameConstants.JETPACK_DURATION, JETPACK_TIMER_TICK) {
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
        player?.isWithJetpack = false
        player?.jump()
        isDisappeared = true
    }

    override fun draw(canvas: Canvas) {
        if (isDisappeared) {
            return
        }

        player?.let {
            if (it.directionX == Player.DirectionX.LEFT) {
                canvas.drawBitmap(
                    initLeftPlayerJetpack,
                    it.x - WIDTH / 2 + OFFSET_ON_PLAYER_LEFT,
                    it.y - HEIGHT / 2,
                    paint
                )
            } else {
                canvas.drawBitmap(
                    initRightPlayerJetpack,
                    it.x - WIDTH / 2 - OFFSET_ON_PLAYER_RIGHT,
                    it.y - HEIGHT / 2,
                    paint
                )
            }
        } ?: run {
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

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    companion object {
        private const val WIDTH = 124f
        private const val HEIGHT = 111f

        private const val OFFSET_ON_PLAYER_RIGHT = 95f
        private const val OFFSET_ON_PLAYER_LEFT = 127f

        private const val JETPACK_TIMER_TICK : Long = 500
        private const val WHEN_TO_PULSE : Long = 2000

        private const val DEFAULT_TRANSPARENCY = 256
        private const val PULSE_TRANSPARENCY = 128
    }
}