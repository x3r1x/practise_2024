package com.example.mygame.domain.generator

import com.example.mygame.domain.Enemy
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.platform.Platform
import com.example.mygame.domain.Screen
import com.example.mygame.domain.enemy.Bully
import com.example.mygame.domain.enemy.factory.BullyFactory
import com.example.mygame.domain.enemy.factory.FlyFactory
import com.example.mygame.domain.enemy.factory.NinjaFactory
import kotlin.random.Random

class EnemyGenerator(
    private val screen: Screen
) {
    private val flyFactory = FlyFactory(screen)
    private val bullyFactory = BullyFactory()
    private val ninjaFactory = NinjaFactory(screen)

    fun generateInitialEnemy(platform: Platform) : Enemy {
        val random = Random.nextFloat()

        when {
            random < GameConstants.INITIAL_NINJA_SPAWN_CHANCE -> {
                return ninjaFactory.generateEnemy(platform.x, platform.y - INITIAL_GAP_Y)
            }
            random < GameConstants.INITIAL_FLY_SPAWN_CHANCE -> {
                return flyFactory.generateEnemy(platform.x, platform.y - INITIAL_GAP_Y)
            }
        }

        return bullyFactory.generateEnemy(platform.x, platform.y - INITIAL_GAP_Y)
    }

    fun generateEnemy(platform: Platform): IGameObject? {
        var enemy: Enemy? = null

        var x = Random.nextFloat() * (screen.width - platform.width) + GAP_X

        val random = Random.nextFloat()

        when {
            random < GameConstants.NINJA_SPAWN_CHANCE -> {
                val ninja = ninjaFactory.generateEnemy(x, platform.y)
                enemy = ninja
            }
            random < GameConstants.FLY_SPAWN_CHANCE -> {
                val fly = flyFactory.generateEnemy(x, platform.y)
                enemy = fly
            }
            random < GameConstants.BULLY_SPAWN_CHANCE -> {
                if (x - Bully.WIDTH / 2 < screen.left) {
                    x = Bully.WIDTH / 2
                } else if (x + Bully.WIDTH / 2 > screen.right) {
                    x = screen.right - Bully.WIDTH / 2
                }

                val bully = bullyFactory.generateEnemy(x, platform.y)

                enemy = bully
            }
        }

        return enemy
    }

    companion object {
        private const val INITIAL_GAP_Y = 110f

        private const val GAP_X = 275f
    }
}