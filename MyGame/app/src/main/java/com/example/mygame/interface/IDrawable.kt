package com.example.mygame.`interface`

import android.graphics.Canvas

interface IDrawable {
    var x: Float
    var y: Float

    fun draw(canvas: Canvas)

    fun setPosition(newX: Float, newY: Float)

    fun updatePositionX(newX: Float)
    fun updatePositionY(elapsedTime: Float)
}