package com.example.mygame.`object`

import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable

class Screen(val width: Float, val height: Float): ICollidable {
    override val left = 0f
    override val right = width
    override val top = 0f
    override val bottom = height

    val maxPlayerHeight = height - MOVE_OBJECTS_LINE

    override fun onObjectCollide(obj: ICollidable) {
    }

    override fun onScreenCollide(screen: Screen) {
    }

    override fun collidesWith(other: ICollidable?): Boolean {
        if (other == null) {
            return false
        }

        val isOutOfSideBounds = other.right < left || other.left > right
        val isOutOfTopBound = other.bottom < top
        val isOutOfBottomBound = other.top > bottom
        return isOutOfSideBounds || isOutOfTopBound || isOutOfBottomBound
    }

    companion object {
        const val MOVE_OBJECTS_LINE = 900f
    }
}