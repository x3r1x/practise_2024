package com.example.mygame.logic

import com.example.mygame.`interface`.IDrawable

class PositionHandler(newX: Float, newY: Float) {
    private var gameElements: List<IDrawable> = emptyList()
    var x = newX
    var y = newY

    fun updateCords(newX: Float, newY: Float) {
        x = newX
        y = newY
    }

    fun updatePositions(elements: List<IDrawable>) {
        elements.forEach {
            it.updatePosition(x, y)
        }
    }
}