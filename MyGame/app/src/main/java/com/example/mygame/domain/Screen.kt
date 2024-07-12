package com.example.mygame.domain

class Screen(val width: Float, val height: Float) {
    val left = 0f
    val right = width
    val top = 0f
    val bottom = height

    val maxPlayerHeight = height - GameConstants.MOVE_OBJECTS_LINE
}