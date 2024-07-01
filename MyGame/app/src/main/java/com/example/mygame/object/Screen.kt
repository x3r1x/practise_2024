package com.example.mygame.`object`

import com.example.mygame.`interface`.ICollidable

class Screen(val width: Float, height: Float): ICollidable {
    override val left = 0f
    override val right = width
    override val top = 0f
    override val bottom = height

    override fun behaviour() {
    }

    override fun screenBehaviour(screen: Screen) {
    }

    override fun collidesWith(other: ICollidable?): Boolean? {
        return null
    }
}