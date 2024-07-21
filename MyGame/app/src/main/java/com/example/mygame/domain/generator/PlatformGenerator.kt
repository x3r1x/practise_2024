package com.example.mygame.domain.generator

import kotlin.math.abs
import kotlin.random.Random
import android.content.res.Resources
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Screen
import com.example.mygame.domain.Platform
import com.example.mygame.domain.platform.factory.IPlatformFactory
import com.example.mygame.domain.platform.factory.StaticPlatformFactory
import com.example.mygame.domain.platform.factory.MovingPlatformOnXFactory
import com.example.mygame.domain.platform.factory.MovingPlatformOnYFactory
import com.example.mygame.domain.platform.factory.DisappearingPlatformFactory

class PlatformGenerator(
    resources: Resources,
    private val screen: Screen
) {
    private val staticPlatformFactory = StaticPlatformFactory(resources)
    private val movingPlatformOnXFactory = MovingPlatformOnXFactory(resources)
    private val disappearingPlatformFactory = DisappearingPlatformFactory(resources)

    private val movingPlatformOnYFactory = MovingPlatformOnYFactory(resources)

    private val platformGap: Float = 50f

    private val maxVerticalGap = 400f

    private val newPackageHeight = 4500f

    private val factories = listOf(
        staticPlatformFactory,
        movingPlatformOnYFactory,
        movingPlatformOnXFactory,
        disappearingPlatformFactory
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

    fun generatePlatform(from: Float): Platform {
        val factory = getRandomFactory()
        val x = Random.nextFloat() * (screen.width - platform.width) + GameConstants.PLATFORM_SPAWN_ADDITIONAL_X
        val y = from - Random.nextFloat() * (maxVerticalGap) - platformGap

        return factory.generatePlatform(x, y)
    }

    private fun getRandomFactory(): IPlatformFactory {
        return factories[Random.nextInt(factories.size)]
    }
}