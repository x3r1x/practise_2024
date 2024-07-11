package com.example.mygame.generator

import android.content.res.Resources
import com.example.mygame.factory.enemy.BullyFactory
import com.example.mygame.factory.enemy.FlyFactory
import com.example.mygame.factory.enemy.NinjaFactory
import com.example.mygame.`interface`.IEnemyFactory
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Screen
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

    fun generateEnemies(platforms: List<Platform>): MutableList<Enemy> {
        val enemies: MutableList<Enemy> = mutableListOf()

        platforms.forEach {
            val random = Random.nextFloat()
            val x = Random.nextFloat() * (screen.width - it.width) + 100f
            when {
                random < 0.1f-> {
                    val fly = flyFactory.generateEnemy(x, it.top)
                    enemies.add(fly)
                }
                random < 0.15f -> {
                    val bully = bullyFactory.generateEnemy(x, it.top)
                    enemies.add(bully)
                }
                random < 0.25f -> {
                    val ninja = ninjaFactory.generateEnemy(x, it.top)
                    enemies.add(ninja)
                }
            }
        }

        return enemies
    }

    fun generateEnemy(platform: Platform): IGameObject? {
        var enemy: Enemy? = null
        val random = Random.nextFloat()
        val x = Random.nextFloat() * (screen.width - platform.width) + 100f
        when {
            random < 0.01f-> {
                val fly = flyFactory.generateEnemy(x, platform.y)
                enemy = fly
            }
            random < 0.02f -> {
                val bully = bullyFactory.generateEnemy(x, platform.y)
                enemy = bully
            }
            random < 0.03f -> {
                val ninja = ninjaFactory.generateEnemy(x, platform.y)
                enemy = ninja
            }
        }

        return enemy
    }

    private fun getRandomFactory(): IEnemyFactory {
        // TODO: Сделать шанс генерации на основе score
        return factories[Random.nextInt(factories.size)]
    }
}