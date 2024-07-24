package com.example.mygame.domain

import android.os.CountDownTimer
import com.example.mygame.domain.player.Player

open class Enemy(createdX: Float, createdY: Float) : IMoveable, IGameObject {
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
        val animator = object : CountDownTimer(MAX_ANIMATION_LENGTH, ANIMATION_TICK) {
            override fun onTick(p0: Long) {
                setPosition(x, y + DEATH_OFFSET)
            }

            override fun onFinish() {}
        }

        animator.start()
    }

    override fun setPosition(startX: Float, startY: Float) {
        x = startX
        y = startY
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    companion object {
        private const val DEATH_OFFSET = 10f
        private const val MAX_ANIMATION_LENGTH : Long = 2000
        private const val ANIMATION_TICK : Long = 1
    }
}