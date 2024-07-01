package com.example.mygame.`interface`

import android.graphics.Canvas

interface IDrawable {
    fun draw(canvas: Canvas)
    fun setPosition(startX: Float, startY: Float)
    fun updatePosition(newX: Float, newY: Float)
}