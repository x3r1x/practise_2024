package com.example.mygame.`interface`

import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player

interface IVisitor {
    fun visit(platform: Platform)
    fun visit(player: Player)
    //with enemy, with bonus, with player
}