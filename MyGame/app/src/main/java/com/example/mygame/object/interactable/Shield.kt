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

class Shield(private val resources: Resources) : IDrawable, IMoveable, IGameObject {
    private var isOnPlayer = false

    private var bitmap = BitmapFactory.decodeResource(resources, DEFAULT_IMAGE, BITMAP_OPTIONS)
    private var paint = Paint().apply {
        alpha = DEFAULT_TRANSPARENCY
    }

    override var x: Float = 0f
    override var y: Float = 0f

    override var left = 0f
    override var right = 0f
    override var top = 0f
    override var bottom = 0f

    override var isDisappeared = false

    private lateinit var player: Player

    fun createOnPlatform(platform: Platform) {
        setPosition(platform.x, platform.top - DEFAULT_SIDE / 2 - OFFSET)
    }

    fun convertShield() {
        bitmap = BitmapFactory.decodeResource(resources, ON_PLAYER_IMAGE, BITMAP_OPTIONS)
    }

    fun initPlayer(entity: Player) {
        player = entity
        isOnPlayer = true
    }

    //thanks god for ending my suffering
    fun startDisappearingTimer() {
        val timer = object : CountDownTimer(SHIELD_DURATION, SHIELD_TIMER_TICK) {
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
        isOnPlayer = false
        isDisappeared = true
        left = 0f
        right = 0f
        top = 0f
        bottom = 0f
    }

    override fun draw(canvas: Canvas) {
        if (isOnPlayer) {
            canvas.drawBitmap(bitmap, player.x - ON_PLAYER_SIDE / 2, player.y - ON_PLAYER_SIDE / 2, paint)
        } else if (!isDisappeared) {
            canvas.drawBitmap(bitmap, left, top, null)
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

    override fun updatePositionX(newX: Float) {}
    override fun updatePositionY(elapsedTime: Float) {}

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    companion object {
        private const val OFFSET = 25f
        private const val DEFAULT_SIDE = 100f
        private const val ON_PLAYER_SIDE = 245f

        private const val SHIELD_DURATION : Long = 10000
        private const val SHIELD_TIMER_TICK : Long = 500
        private const val WHEN_TO_PULSE : Long = 3000

        private const val DEFAULT_TRANSPARENCY = 128
        private const val PULSE_TRANSPARENCY = 64

        private val DEFAULT_IMAGE = R.drawable.shield
        private val ON_PLAYER_IMAGE = R.drawable.shield_on_player

        private val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}