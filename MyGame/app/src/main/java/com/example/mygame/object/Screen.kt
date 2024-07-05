package com.example.mygame.`object`

import com.example.mygame.`interface`.ICollidable

class Screen(val width: Float, val height: Float): ICollidable {
    override val left = 0f
    override val right = width
    override val top = 0f
    override val bottom = height

    override val isPassable = false

    override var isInSpring: Boolean? = null

    val borderLine = height - MOVE_OBJECTS_LINE

    override fun onObjectCollide(obj: ICollidable) {
    }

    override fun onScreenCollide(screen: Screen) {
    }

    override fun collidesWith(other: ICollidable?): Boolean? {
        return null
    }

    companion object {
        const val MOVE_OBJECTS_LINE = 900f
    }
}