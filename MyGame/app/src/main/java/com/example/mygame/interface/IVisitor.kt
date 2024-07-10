package com.example.mygame.`interface`

import com.example.mygame.`object`.Score
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Platform

interface IVisitor {
    fun visit(platform: Platform)
    fun visit(player: Player)
    fun visit(score: Score)
    //with enemy, with bonus, with player
}