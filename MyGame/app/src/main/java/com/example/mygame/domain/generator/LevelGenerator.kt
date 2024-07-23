package com.example.mygame.domain.generator

import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.Platform
import com.example.mygame.domain.Screen
import com.example.mygame.domain.platform.BreakingPlatform
import com.example.mygame.domain.platform.StaticPlatform
import kotlin.math.abs

class LevelGenerator(
    screen: Screen
) {
    private val platformGenerator = PlatformGenerator(screen)
    private val enemyGenerator = EnemyGenerator(screen)
    private val bonusGenerator = BonusGenerator()

    private val newPackageHeight = 4000f

    fun generateInitialPack(): MutableList<IGameObject> {
        val initialPack = mutableListOf<IGameObject>()

        val platforms = platformGenerator.generateInitialPlatforms().toMutableList()
        val randomPlatformsWithBonuses = platforms.shuffled().take(GameConstants.BONUSES_IN_INITIAL_PACK)
        val randomPlatformsWithEnemies = platforms.shuffled().take(GameConstants.ENEMIES_IN_INITIAL_PACK)

        platforms.forEach {
            if (it in randomPlatformsWithBonuses) {
                initialPack.add(bonusGenerator.generateInitialBonus(it) as IGameObject)
            }

            if (it in randomPlatformsWithEnemies) {
                initialPack.add(enemyGenerator.generateInitialEnemy(it))
            }

            initialPack.add(it)
        }

        return initialPack.toMutableList()
    }

    fun generateNewPack(from: Float): MutableList<IGameObject> {
        var newY = from

        var nextStaticPlatformsCount = 0

        val level = mutableListOf<IGameObject>()

        while (abs(newY) < abs(newPackageHeight + from)) {
            var bonusSpawned = false
            var platform : Platform? = null

            val pack = mutableListOf<IGameObject>()

            if (nextStaticPlatformsCount == 0) {
                platform = platformGenerator.generatePlatform(newY)

                if (platform is BreakingPlatform) {
                    val postPlatform = platformGenerator.generatePlatform(platform.top, true)
                    pack.add(postPlatform)
                }
                pack.add(platform)

                if (platform is StaticPlatform) {
                    nextStaticPlatformsCount = GameConstants.STATIC_PLATFORM_COUNT_PER_LEVEL - 1
                }
            } else {
                platform = platformGenerator.generateStaticPlatform(newY)
                pack.add(platform)
                nextStaticPlatformsCount--
            }

            (bonusGenerator.generateBonus(platform) as IGameObject?)?.let {
                    bonus -> pack.add(bonus)
                bonusSpawned = true
            }

            enemyGenerator.generateEnemy(platform)?.let {
                    enemy ->
                if (!bonusSpawned) {
                    pack.add(enemy)
                    pack.remove(platform)
                    pack.add(platformGenerator.generatePlatform(newY))
                }
            }

            newY = pack.last().top

            level.addAll(pack)
        }

        return level
    }
}