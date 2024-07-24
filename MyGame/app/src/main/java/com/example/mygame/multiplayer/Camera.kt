package com.example.mygame.multiplayer

import com.example.mygame.domain.Screen

class Camera(
    screen: Screen
) {
    private val fixedY = screen.height / 3 * 2
    private val fixedX = screen.width / 2

    private val bottom  = 60f

    private var offsetY = 0f

    fun getOffsetY() : Float {
        return offsetY + 75f
    }

    fun getOffsetX(playerX: Float) : Float {
        return fixedX - playerX
    }

    fun countOffsetY(playerY: Float) {
        offsetY = fixedY - playerY
    }

}