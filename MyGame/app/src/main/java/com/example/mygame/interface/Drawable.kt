package com.example.mygame.`interface`

import android.graphics.Canvas

interface Drawable {
    fun draw(canvas: Canvas)
    fun updatePosition(newX:Float, newY:Float)
}