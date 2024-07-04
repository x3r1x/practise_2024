//Будет использован, когда будет готова физика
package com.example.mygame.logic

import android.util.Log
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.platforms.MovingPlatformOnY

class PositionHandler(entities: List<IDrawable>) {
    private val elements = entities

    fun updatePositions(offsetX: Float, offsetY: Float) {
        elements.forEach {
            it.setPosition(it.x - offsetX, it.y - offsetY)
        }
    }
}