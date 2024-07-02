package com.example.mygame.`object`

import com.example.mygame.`interface`.ICollidable

class Screen(val width: Float, val height: Float): ICollidable {
    override val left = 0f
    override val right = width
    override val top = 0f
    override val bottom = height

    val borderLine = height - MOVE_OBJECTS_LINE

    override fun behaviour(platformTop: Float) {
    }

    override fun screenBehaviour(screen: Screen) {
    }

    override fun collidesWith(other: ICollidable?): Boolean? {
        return null
    }

    companion object {
        const val MOVE_OBJECTS_LINE = 3000f
    }
}