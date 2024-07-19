package com.example.mygame.domain.enemies.factory

import com.example.mygame.domain.Enemy
import com.example.mygame.domain.enemies.Bully

class BullyFactory() : IEnemyFactory {
    override fun generateEnemy(createdX: Float, createdY: Float): Enemy {
        return Bully(createdX, createdY)
    }
}