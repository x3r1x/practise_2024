package com.example.mygame.domain.generator

import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.platform.Platform
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

    private val newPackageHeight = 6000f

    private val factories = listOf(
        staticPlatformFactory,
        movingPlatformOnYFactory,
        movingPlatformOnXFactory,
        disappearingPlatformFactory,
        breakingPlatformFactory
    )

    private val platform = Platform(0f, 0f)

    fun generateInitialPlatforms(): MutableList<Platform> {
        val platforms = mutableListOf<Platform>()

        var newY = screen.height - OTHER_PLATFORMS_OUT_OF_START

        while (newY >= -newPackageHeight) {
            val x = Random.nextFloat() * (screen.width - Platform.WIDTH) + GameConstants.PLATFORM_SPAWN_ADDITIONAL_X
            val newPlatform = staticPlatformFactory.generatePlatform(x, newY)
            platforms.add(newPlatform)
            newY -= Platform.HEIGHT + platformGap
        }

        return platforms
    }

    fun generateStaticPlatform(from: Float) : Platform {
        val coordinates = getRandomCoordinates(from, GameConstants.MAX_VERTICAL_PLATFORM_GAP)

        return staticPlatformFactory.generatePlatform(coordinates[0], coordinates[1])
    }

    fun generatePlatform(from: Float, currentScore: Int, isNotBreaking: Boolean = false): Platform {
        var factory = getRandomFactory()
        if (factory is BreakingPlatformFactory && isNotBreaking) {
            while (factory is BreakingPlatformFactory) {
                factory = getRandomFactory()
            }
        }

        var verticalPlatformGap = GameConstants.MIN_VERTICAL_PLATFORM_GAP + currentScore / GameConstants.PLATFORM_GENERATION_RATIO

        if (verticalPlatformGap > GameConstants.MAX_VERTICAL_PLATFORM_GAP) {
            verticalPlatformGap = GameConstants.MAX_VERTICAL_PLATFORM_GAP
        }

        println("#Debug: $verticalPlatformGap")

        if (isNotBreaking) {
            verticalPlatformGap = GameConstants.MAX_VERTICAL_BREAKING_PLATFORM_GAP
        }

        val coordinates = getRandomCoordinates(from, verticalPlatformGap)

        return factory.generatePlatform(coordinates[0], coordinates[1])
    }

    fun generateStartPlatform() : MutableList<Platform> {
        var initialPlatform = StaticPlatformFactory().generatePlatform(screen.left + Platform.WIDTH / 2,
            screen.bottom - Platform.HEIGHT / 2 - START_PLATFORMS_GAP_OF_SCREEN)

        val platforms = mutableListOf<Platform>()

        while (initialPlatform.right < screen.width) {
            platforms.add(initialPlatform)

            val previousRight = initialPlatform.right

            initialPlatform = StaticPlatformFactory().generatePlatform(previousRight + START_PLATFORM_GAP + Platform.WIDTH / 2,
                screen.bottom - Platform.HEIGHT / 2 - START_PLATFORMS_GAP_OF_SCREEN)
        }

        platforms.add(initialPlatform)

        return platforms
    }

    private fun getRandomCoordinates(from: Float, verticalGap: Float) : Array<Float> {
        val x = Random.nextFloat() * (screen.width - Platform.WIDTH) + GameConstants.PLATFORM_SPAWN_ADDITIONAL_X
        val y = from - Random.nextFloat() * (verticalGap - platformGap) - platformGap
        return arrayOf(x, y)
    }

    private fun getRandomFactory(): IPlatformFactory {
        return factories[Random.nextInt(factories.size)]
    }

    companion object {
        private const val START_PLATFORMS_GAP_OF_SCREEN = 200f
        private const val START_PLATFORM_GAP = 10f

        private const val OTHER_PLATFORMS_OUT_OF_START = 600f
    }
}