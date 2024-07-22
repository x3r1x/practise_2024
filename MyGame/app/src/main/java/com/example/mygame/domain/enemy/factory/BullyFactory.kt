package com.example.mygame.domain.enemy.factory

import com.example.mygame.domain.Enemy
import com.example.mygame.domain.enemy.Bully

class BullyFactory() : IEnemyFactory {
    override fun generateEnemy(createdX: Float, createdY: Float): Enemy {
        return Bully(createdX, createdY)
    }
}