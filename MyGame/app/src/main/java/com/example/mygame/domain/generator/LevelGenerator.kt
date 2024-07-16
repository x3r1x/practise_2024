package com.example.mygame.domain.generator

import android.content.res.Resources
import com.example.mygame.domain.Screen
import com.example.mygame.domain.IGameObject
import kotlin.math.abs

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
        // TODO: добавить генерацию ломающихся платформ
        while (abs(newY) < abs(newPackageHeight + from)) {
            var bonusSpawned = false
            val pack = mutableListOf<IGameObject>()
            val platform = platformGenerator.generatePlatform(newY)
            pack.add(platform)

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