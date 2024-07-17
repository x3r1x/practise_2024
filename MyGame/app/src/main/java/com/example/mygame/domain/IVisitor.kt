package com.example.mygame.domain

import com.example.mygame.domain.bonuses.Jetpack
import com.example.mygame.domain.bonuses.Shield
import com.example.mygame.domain.bonuses.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.enemies.Enemy
import com.example.mygame.domain.player.Player

interface IVisitor {
    fun visit(platform: Platform)
    fun visit(player: Player)
    fun visit(spring: Spring)
    fun visit(shield: Shield)
    fun visit(jetpack: Jetpack)
    fun visit(enemy: Enemy)
    fun visit(bullet: Bullet)
    //with enemy, with bonus, with player
}