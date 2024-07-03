package com.example.mygame

import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform

class Physics {
    fun doWeNeedToMove(player: Ball, line: Float) : Boolean {
        return player.top < line
    }

    fun moveOffset(player: Ball, line: Float) : Float {
        return player.top - line
    }

    companion object {
        const val GRAVITY = 900f
    }
}