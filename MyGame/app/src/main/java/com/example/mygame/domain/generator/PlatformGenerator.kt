package com.example.mygame.domain.generator

import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.GameConstants.Companion.MAX_VERTICAL_BREAKING_PLATFORM_GAP
import com.example.mygame.domain.GameConstants.Companion.MAX_VERTICAL_PLATFORM_GAP
import com.example.mygame.domain.Platform
import com.example.mygame.domain.Screen
import com.example.mygame.domain.platform.factory.BreakingPlatformFactory
import com.example.mygame.domain.platform.factory.DisappearingPlatformFactory
import com.example.mygame.domain.platform.factory.IPlatformFactory
import com.example.mygame.domain.platform.factory.MovingPlatformOnXFactory
import com.example.mygame.domain.platform.factory.MovingPlatformOnYFactory
import com.example.mygame.domain.platform.factory.StaticPlatformFactory
import kotlin.random.Random

class PlatformGenerator(
    private val screen: Screen
) {
    private val staticPlatformFactory = StaticPlatformFactory()
    private val breakingPlatformFactory = BreakingPlatformFactory()
    private val movingPlatformOnXFactory = MovingPlatformOnXFactory()
    private val disappearingPlatformFactory = DisappearingPlatformFactory()
    private val movingPlatformOnYFactory = MovingPlatformOnYFactory()

    private val platformGap: Float = 100f

    private val newPackageHeight = 4500f

    private val factories = listOf(
        staticPlatformFactory,
        movingPlatformOnYFactory,
        movingPlatformOnXFactory,
        disappearingPlatformFactory,
        breakingPlatformFactory
    )

    private val platform = Platform(0f, 0f)

    fun generateInitialPlatforms(): MutableList<Platform> {
        var newY = screen.height

        val platforms: MutableList<Platform> = mutableListOf()
        while (newY >= -newPackageHeight) {
            val x = Random.nextFloat() * (screen.width - platform.width) + GameConstants.PLATFORM_SPAWN_ADDITIONAL_X
            val newPlatform = staticPlatformFactory.generatePlatform(x, newY)
            platforms.add(newPlatform)
            newY -= platform.height + platformGap
        }

        return platforms
    }

    fun generatePlatform(from: Float, isNotBreaking: Boolean = false): Platform {
        var factory = getRandomFactory()
        if (factory is BreakingPlatformFactory && isNotBreaking) {
            while (factory is BreakingPlatformFactory) {
                factory = getRandomFactory()
            }
        }
        var verticalPlatformGap = MAX_VERTICAL_PLATFORM_GAP
        if (isNotBreaking) {
            verticalPlatformGap = MAX_VERTICAL_BREAKING_PLATFORM_GAP
        }

        val x = Random.nextFloat() * (screen.width - platform.width) + GameConstants.PLATFORM_SPAWN_ADDITIONAL_X
        val y = from - Random.nextFloat() * (verticalPlatformGap) - platformGap

        return factory.generatePlatform(x, y)
    }

    private fun getRandomFactory(): IPlatformFactory {
        // TODO: Сделать шанс генерации на основе score
        return factories[Random.nextInt(factories.size)]
    }
}