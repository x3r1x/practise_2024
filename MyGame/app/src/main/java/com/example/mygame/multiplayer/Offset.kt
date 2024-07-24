package com.example.mygame.multiplayer

import com.example.mygame.domain.Screen

class Offset(
    screen: Screen
) {
    private val fixedX = screen.width / 2
    private val fixedY = screen.height / 3 * 2

    private var offsetX = 0f
    private var offsetY = 0f

    fun getX(): Float {
        return offsetX
    }

    fun getY(): Float {
        return offsetY
    }

    fun calcFrom(x: Float, y: Float) {
        offsetX = fixedX - x
        offsetY = fixedY - y
    }
}