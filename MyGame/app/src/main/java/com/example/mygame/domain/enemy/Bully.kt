package com.example.mygame.domain.enemy

import com.example.mygame.domain.Enemy

class Bully(
            createdX: Float,
            createdY: Float
) : Enemy(createdX, createdY) {

    override val width: Float
        get() = WIDTH

    override val height: Float
        get() = HEIGHT

    companion object {
        private const val WIDTH = 275f
        private const val HEIGHT = 174f
    }
}