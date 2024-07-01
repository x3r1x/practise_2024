package com.example.mygame.`interface`

import com.example.mygame.`object`.Screen

interface ICollidable {
    val left: Float
    val right: Float
    val top: Float
    val bottom: Float

    fun behaviour()

    fun screenBehaviour(screen: Screen)

    fun collidesWith(other: ICollidable?): Boolean?
}