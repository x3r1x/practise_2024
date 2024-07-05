package com.example.mygame.`interface`

interface IMoveable {
    var x: Float
    var y: Float

    fun setPosition(startX: Float, startY: Float)

    fun updatePositionX(newX: Float)
    fun updatePositionY(elapsedTime: Float)
}