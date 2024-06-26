package com.example.mygame

import com.example.mygame.`interface`.Drawable

class PositionHandler(posX: Float, posY: Float) {

    private var newX = posX
    private var newY = posY

    private var gameElements: List<Drawable> = emptyList()

    fun updateCords(x: Float, y: Float) {
        this.newX = x
        this.newY = y
    }

    fun updatePositions(elements: List<Drawable>) {
        elements.forEach {
            it.updatePosition(newX, newY)
        }
    }
}