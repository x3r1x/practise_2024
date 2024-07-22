package com.example.mygame.domain.bonus

import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.player.Player

class Shield(
    createdX: Float,
    createdY: Float
) : IMoveable, IBonus, IGameObject {
    override var x: Float = createdX
    override var y: Float = createdY

    override var left = x - DEFAULT_SIDE / 2
    override var right = x + DEFAULT_SIDE / 2
    override var top = y - DEFAULT_SIDE / 2
    override var bottom = y + DEFAULT_SIDE / 2

    override var isDisappeared = false

    fun select(entity: Player) {
        entity.bonuses.selectShield()
        isDisappeared = true
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
    }
}