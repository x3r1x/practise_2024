package com.example.mygame.`interface`

import com.example.mygame.`object`.Score
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.bonuses.Jetpack
import com.example.mygame.`object`.bonuses.Shield
import com.example.mygame.`object`.bonuses.Spring

interface IVisitor {
    fun visit(platform: Platform)
    fun visit(player: Player)
    fun visit(score: Score)
    fun visit(spring: Spring)
    fun visit(shield: Shield)
    fun visit(jetpack: Jetpack)
    fun visit(enemy: Enemy)
    //with enemy, with bonus, with player
}