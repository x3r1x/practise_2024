package com.example.mygame.generator

import android.content.res.Resources
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.platform.StaticPlatform

class LevelGenerator(
    resources: Resources,
    screen: Screen,
    player: Player
) {
    private val platformGenerator = PlatformGenerator(resources, screen)
    private val bonusGenerator = BonusGenerator(resources, player)

    fun generateInitialPack(): MutableList<IGameObject> {
        return platformGenerator.generateInitialPlatforms().toMutableList()
    }

    fun generateNewPack(from: Float): MutableList<IGameObject> {
        val platforms = platformGenerator.generatePlatforms(from - 2500f)
        val staticPlatforms = platforms.filter {
            it::class == StaticPlatform::class
        }

        val bonuses = bonusGenerator.generateBonuses(staticPlatforms)

        val objects = (platforms + bonuses) as MutableList<IGameObject>

        return objects.toMutableList()
    }
}