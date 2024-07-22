package com.example.mygame.domain.bonuses

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.CountDownTimer
import com.example.mygame.UI.GameSoundsPlayer
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.player.Player

class Shield(private val initDefaultShield: Bitmap,
             private val initTransformedShield: Bitmap,
             createdX: Float,
             createdY: Float
) : IDrawable, IMoveable, IBonus, IGameObject {
    private var paint = Paint().apply {
        alpha = DEFAULT_TRANSPARENCY
    }

    override var x: Float = createdX
    override var y: Float = createdY

    override var left = x - DEFAULT_SIDE / 2
    override var right = x + DEFAULT_SIDE / 2
    override var top = y - DEFAULT_SIDE / 2
    override var bottom = y + DEFAULT_SIDE / 2

    override var isDisappeared = false

    private var player: Player? = null

    fun initPlayer(entity: Player) {
        player = entity
        player?.isWithShield = true
    }

    //thanks god for ending my suffering
    fun startDisappearingTimer(audioPlayer: GameSoundsPlayer) {
        audioPlayer.playShieldPickupSound()

        val timer = object : CountDownTimer(GameConstants.SHIELD_DURATION, SHIELD_TIMER_TICK) {
            override fun onTick(p0: Long) {
                if (p0 <= WHEN_TO_PULSE) {
                    paint.alpha = if (paint.alpha == PULSE_TRANSPARENCY) {
                        DEFAULT_TRANSPARENCY
                    } else {
                        audioPlayer.playBonusEndingSoonSound()

                        PULSE_TRANSPARENCY
                    }
                }
            }

            override fun onFinish() {
                audioPlayer.playShieldDestroySound()

                dispose()
            }
        }

        timer.start()
    }

    private fun dispose() {
        player?.isWithShield = false
        isDisappeared = true
        left = 0f
        right = 0f
        top = 0f
        bottom = 0f
    }

    override fun draw(canvas: Canvas) {
        if (isDisappeared) {
            return
        }

        player?.let {
            canvas.drawBitmap(initTransformedShield, it.x - ON_PLAYER_SIDE / 2, it.y - ON_PLAYER_SIDE / 2, paint)
        } ?: run {
            canvas.drawBitmap(initDefaultShield, left, top, null)
        }
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
        left = x - DEFAULT_SIDE / 2
        right = x + DEFAULT_SIDE / 2
        top = y - DEFAULT_SIDE / 2
        bottom = y + DEFAULT_SIDE / 2
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    companion object {
        private const val DEFAULT_SIDE = 100f
        private const val ON_PLAYER_SIDE = 245f

        private const val SHIELD_TIMER_TICK : Long = 500
        private const val WHEN_TO_PULSE : Long = 3000

        private const val DEFAULT_TRANSPARENCY = 128
        private const val PULSE_TRANSPARENCY = 64
    }
}