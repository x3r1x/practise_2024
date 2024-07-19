package com.example.mygame.domain.bonuses

import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.player.Player

class Jetpack(
              createdX: Float,
              createdY: Float
) : IMoveable, IBonus, IGameObject {
    override var x = createdX
    override var y = createdY

    var paint = Paint().apply {
        alpha = DEFAULT_TRANSPARENCY
    }

    override var left = x - WIDTH / 2
    override var top = y - WIDTH / 2
    override var bottom = y + WIDTH / 2
    override var right = x + WIDTH / 2

    override var isDisappeared = false

    private var player: Player? = null

    enum class State {
        UNUSED,
        ON_LEFT_OF_PLAYER,
        ON_RIGHT_OF_PLAYER
    }

    var state = State.UNUSED

    fun initPlayer(entity: Player) {
        player = entity
        player?.isWithJetpack = true
    }

    fun getCoords(): Pair<Float, Float> {
        if (player == null) {
            return Pair(x, y)
        } else {
            if (player!!.directionX == Player.DirectionX.LEFT) {
                state = State.ON_RIGHT_OF_PLAYER
                x = player!!.right + OFFSET_ON_PLAYER
                y = player!!.y
            } else {
                state = State.ON_LEFT_OF_PLAYER
                x = player!!.left - OFFSET_ON_PLAYER
                y = player!!.y
            }
            return Pair(x, y)
        }
    }

    fun fly() {
        player?.directionY = Player.DirectionY.UP
        player?.speedY = GameConstants.PLAYER_SPEED_WITH_JETPACK

        if (player?.directionX == Player.DirectionX.LEFT) {
            state = State.ON_LEFT_OF_PLAYER
        } else {
            state = State.ON_RIGHT_OF_PLAYER
        }
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
        const val WIDTH = 124f
        const val HEIGHT = 111f

        const val OFFSET_ON_PLAYER = 40f

        private const val JETPACK_TIMER_TICK: Long = 500
        private const val WHEN_TO_PULSE: Long = 2000

        private const val DEFAULT_TRANSPARENCY = 255
        private const val PULSE_TRANSPARENCY = 128
    }
}