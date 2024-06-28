package com.example.mygame

import com.example.mygame.`interface`.Drawable

class PositionHandler(newX: Float, newY: Float) {
    private var gameElements: List<Drawable> = emptyList()
    var x = newX
    var y = newY

    fun updateCords(newX: Float, newY: Float) {
        x = newX
        y = newY
    }

    fun updatePositions(elements: List<Drawable>) {
        elements.forEach {
            it.updatePosition(x, y)
        }
    }
}