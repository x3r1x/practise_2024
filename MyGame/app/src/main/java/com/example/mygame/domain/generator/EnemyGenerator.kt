package com.example.mygame.domain.generator

import android.content.res.Resources
import com.example.mygame.domain.enemies.factory.BullyFactory
import com.example.mygame.domain.enemies.factory.FlyFactory
import com.example.mygame.domain.enemies.factory.NinjaFactory
import com.example.mygame.domain.enemies.factory.IEnemyFactory
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.Enemy
import com.example.mygame.domain.Platform
import com.example.mygame.domain.Screen
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
        val x = Random.nextFloat() * (screen.width - platform.width) + GAP_X
        when {
            random < 0.03f -> {
                val bully = bullyFactory.generateEnemy(x, platform.y)
                enemy = bully
            }
            random < 0.05f -> {
                val ninja = ninjaFactory.generateEnemy(x, platform.y)
                enemy = ninja
            }
            random < 0.1f-> {
                val fly = flyFactory.generateEnemy(x, platform.y)
                enemy = fly
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