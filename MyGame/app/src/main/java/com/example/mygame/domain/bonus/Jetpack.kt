package com.example.mygame.domain.bonus

import com.example.mygame.UI.GameSoundsPlayer
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

    override var left = x - WIDTH / 2
    override var top = y - WIDTH / 2
    override var bottom = y + WIDTH / 2
    override var right = x + WIDTH / 2

    override var isDisappeared = false

    fun select(entity: Player, audioPlayer: GameSoundsPlayer) {
        entity.bonuses.selectJetpack(audioPlayer)
        entity.directionY = Player.DirectionY.UP
        entity.speedY = GameConstants.PLAYER_SPEED_WITH_JETPACK
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
    }
}