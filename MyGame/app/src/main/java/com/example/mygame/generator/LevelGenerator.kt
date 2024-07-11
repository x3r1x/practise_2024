package com.example.mygame.generator

import android.content.res.Resources
import com.example.mygame.`interface`.IBonus
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.platform.StaticPlatform
import kotlin.math.abs
import kotlin.random.Random

class LevelGenerator(
    resources: Resources,
    screen: Screen
) {
    private val platformGenerator = PlatformGenerator(resources, screen)
    private val enemyGenerator = EnemyGenerator(resources, screen)
    private val bonusGenerator = BonusGenerator(resources)

    private val newPackageHeight = 4500f

    fun generateInitialPack(): MutableList<IGameObject> {
        return platformGenerator.generateInitialPlatforms().toMutableList()
    }

    fun generateNewPack(from: Float): MutableList<IGameObject> {
        var newY = from

        val level = mutableListOf<IGameObject>()

        while (abs(newY) < abs(newPackageHeight + from)) {
            val pack = mutableListOf<IGameObject>()
            val platform = platformGenerator.generatePlatform(newY)
            pack.add(platform)

            (bonusGenerator.generateBonus(platform) as IGameObject?)?.let {
                bonus -> pack.add(bonus)
            }

            enemyGenerator.generateEnemy(platform)?.let {
                enemy -> pack.add(enemy); pack.remove(platform)
            }

            newY = pack.last().top

            level.addAll(pack)
        }

        return level
    }
}