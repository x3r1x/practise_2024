package com.example.mygame.domain.enemy.factory

import com.example.mygame.domain.Enemy

interface IEnemyFactory {
    fun generateEnemy(createdX: Float, createdY: Float) : Enemy
}