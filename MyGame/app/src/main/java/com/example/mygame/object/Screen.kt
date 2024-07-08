package com.example.mygame.`object`

class Screen(val width: Float, val height: Float) {
    val left = 0f
    val right = width
    val top = 0f
    val bottom = height

    val maxPlayerHeight = height - MOVE_OBJECTS_LINE
    
    override val isPassable = false

    override var isInSpring: Boolean? = null

    companion object {
        const val MOVE_OBJECTS_LINE = 900f
    }
}