package com.example.mygame.`object`

import com.example.mygame.`interface`.ICollidable

class Screen(val width: Float, val height: Float): ICollidable {
    override val left = 0f
    override val right = width
    override val top = 0f
    override val bottom = height

    override fun onObjectCollide() {
    }

    override fun onScreenCollide(screen: Screen) {
    }

    override fun collidesWith(other: ICollidable?): Boolean? {
        return null
    }
}