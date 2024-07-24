package com.example.mygame.multiplayer

import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.view.ObjectView

class Offset(
    screen: Screen
) {
    private val fixedY = screen.height / 3 * 2
    private val fixedX = screen.width / 2

    private var offsetY = 0f
    private var offsetX = 0f

    fun updatePosition(elements: List<ObjectView>) {
        elements.forEach {
            it.y += offsetY
        }
    }

    fun getY() : Float {
        return offsetY + 75f
    }

    fun getOffsetX(playerX: Float) : Float {
        return fixedX - playerX
    }

    fun calcFrom(y: Float): Float {
        offsetY = fixedY - y
        return offsetY
    }
}