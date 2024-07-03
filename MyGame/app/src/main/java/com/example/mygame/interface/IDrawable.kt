package com.example.mygame.`interface`

import android.graphics.Canvas

interface IDrawable {
    var x: Float
    var y: Float

    fun draw(canvas: Canvas)

    fun setPosition(startX: Float, startY: Float)

    fun updatePositionX(newX: Float)
    fun updatePositionY(previousY: Float, elapsedTime: Float)
}