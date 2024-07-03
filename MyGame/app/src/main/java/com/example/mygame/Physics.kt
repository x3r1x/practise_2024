package com.example.mygame

import com.example.mygame.`object`.Player

class Physics {
    fun doWeNeedToMove(player: Player, line: Float) : Boolean {
        return player.top < line
    }

    fun moveOffset(player: Player, line: Float) : Float {
        return player.top - line
    }

    companion object {
        const val GRAVITY = 900f
    }
}