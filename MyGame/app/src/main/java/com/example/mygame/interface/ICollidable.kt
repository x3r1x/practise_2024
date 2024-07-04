package com.example.mygame.`interface`

import com.example.mygame.`object`.Screen

//Сделать поле: Rect, с помощью которого мы будем высчитывать коллизию

interface ICollidable {
    val left: Float
    val right: Float
    val top: Float
    val bottom: Float

    var isInSpring: Boolean?

    fun onObjectCollide(obj: ICollidable)

    fun onScreenCollide(screen: Screen)

    fun collidesWith(other: ICollidable?): Boolean?
}