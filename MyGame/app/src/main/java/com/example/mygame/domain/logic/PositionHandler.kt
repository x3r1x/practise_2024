package com.example.mygame.domain.logic

import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.player.Player

class PositionHandler(entities: List<IMoveable>) {
    private val elements = entities

    // при движении экрана
    fun screenScroll(offsetX: Float, offsetY: Float) {
        elements.forEach {
            it.setPosition(it.x - offsetX, it.y - offsetY)
        }
    }

    fun updatePositions(deltaX: Float, elapsedTime: Float) {
        elements.forEach {
            if (it is Player) {
                it.updatePositionX(deltaX + deltaX * elapsedTime)
            } else {
                it.updatePositionX(0f) //?
            }
            it.updatePositionY(elapsedTime)
        }
    }
}