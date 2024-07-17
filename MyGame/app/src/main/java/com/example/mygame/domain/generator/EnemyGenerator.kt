package com.example.mygame.domain.generator

import android.content.res.Resources
import com.example.mygame.domain.enemies.factory.BullyFactory
import com.example.mygame.domain.enemies.factory.FlyFactory
import com.example.mygame.domain.enemies.factory.NinjaFactory
import com.example.mygame.domain.enemies.factory.IEnemyFactory
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.enemies.Enemy
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Platform
import com.example.mygame.domain.Screen
import com.example.mygame.domain.enemies.Bully
import kotlin.random.Random

class EnemyGenerator(
    resources: Resources,
    private val screen: Screen
) {
    private val flyFactory = FlyFactory(resources, screen)
    private val bullyFactory = BullyFactory(resources)
    private val ninjaFactory = NinjaFactory(resources, screen)

    private val factories = listOf(
        flyFactory,
        bullyFactory,
        ninjaFactory
    )

    fun generateEnemy(platform: Platform): IGameObject? {
        var enemy: Enemy? = null
        val random = Random.nextFloat()

        var x = Random.nextFloat() * (screen.width - platform.width) + GAP_X

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

    private fun getRandomFactory(): IEnemyFactory {
        // TODO: Сделать шанс генерации на основе score
        return factories[Random.nextInt(factories.size)]
    }

    companion object {
        private const val GAP_X = 275f
    }
}