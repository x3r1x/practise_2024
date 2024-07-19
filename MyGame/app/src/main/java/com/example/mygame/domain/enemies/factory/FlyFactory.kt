package com.example.mygame.domain.enemies.factory

import com.example.mygame.domain.Enemy
import com.example.mygame.domain.Screen
import com.example.mygame.domain.enemies.Fly

class FlyFactory(
    private val screen: Screen
) : IEnemyFactory {

    override fun generateEnemy(createdX: Float, createdY: Float): Enemy {
        return Fly(createdX, createdY, screen)
    }
}