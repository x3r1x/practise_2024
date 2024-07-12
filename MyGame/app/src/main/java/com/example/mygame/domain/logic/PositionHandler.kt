package com.example.mygame.domain.logic

import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.player.Player

class PositionHandler() {
    // при движении экрана
    fun screenScroll(elements: List<IMoveable>, offsetX: Float, offsetY: Float) {
        elements.forEach {
            it.setPosition(it.x - offsetX, it.y - offsetY)
        }
    }

    fun updatePositions(elements: List<IMoveable>, elapsedTime: Float) {
        elements.forEach {
            it.updatePosition(elapsedTime)
        }
    }
}