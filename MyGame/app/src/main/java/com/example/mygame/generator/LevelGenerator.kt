package com.example.mygame.generator

import android.content.res.Resources
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.platform.StaticPlatform

class LevelGenerator(
    resources: Resources,
    screen: Screen,
    player: Player
) {
    private val platformGenerator = PlatformGenerator(resources, screen)
    private val bonusGenerator = BonusGenerator(resources, player)

    fun generateInitialPack(): MutableList<IDrawable> {
        return platformGenerator.generateInitialPlatforms() as MutableList<IDrawable>
    }

    fun generateNewPack(): MutableList<IDrawable> {
        val platforms = platformGenerator.generatePlatforms()
        val staticPlatforms = platforms.filter {
            it::class == StaticPlatform::class
        }

        val bonuses = bonusGenerator.generateBonuses(staticPlatforms)

        val objects = platforms + bonuses as MutableList<IDrawable>

        return objects.toMutableList()
    }
}