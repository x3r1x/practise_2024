package com.example.mygame.generator

import kotlin.random.Random
import android.content.res.Resources
import com.example.mygame.`object`.Platform
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.factories.platforms.StaticPlatformFactory
import com.example.mygame.factories.platforms.BreakingPlatformFactory
import com.example.mygame.factories.platforms.MovingPlatformOnXFactory
import com.example.mygame.factories.platforms.MovingPlatformOnYFactory
import com.example.mygame.factories.platforms.DisappearingPlatformFactory

class PlatformGenerator(
    var resources: Resources,
    private val screenWidth: Float,
    private val screenHeight: Float
) {
    private var nextY: Float = screenHeight

    private val staticPlatformFactory = StaticPlatformFactory(resources)
    private val breakingPlatformFactory = BreakingPlatformFactory(resources)
    private val disappearingPlatformFactory = DisappearingPlatformFactory(resources)
    private val movingPlatformOnXFactory = MovingPlatformOnXFactory(resources)
    private val movingPlatformOnYFactory = MovingPlatformOnYFactory(resources)

    private val platform = Platform(0f, 0f)

    private val platformGap: Float = 20f

    private val factories = listOf(
        staticPlatformFactory,
        disappearingPlatformFactory,
        movingPlatformOnYFactory,
        movingPlatformOnXFactory
    )

    private val activePlatforms: MutableList<Platform> = mutableListOf()

    init {
        generateInitialPlatforms()
    }

    fun getActivePlatforms(): List<Platform> {
        return activePlatforms
    }

    fun generatePlatformsIfNeeded() {
        if (activePlatforms.lastIndex < 40) {
            generatePlatforms()
        }
    }

    fun update() {
        val iterator = activePlatforms.iterator()
        while (iterator.hasNext()) {
            val platform = iterator.next()
            if (platform.top > screenHeight) {
                iterator.remove()
            }
        }
    }

    private fun getRandomFactory(): IPlatformFactory {
        return factories[Random.nextInt(factories.size)]
    }

    private fun generateInitialPlatforms() {
        while (nextY >= 0) {
            val x = Random.nextFloat() * (screenWidth - platform.width)
            val newPlatform = staticPlatformFactory.generatePlatform(x, nextY)
            activePlatforms.add(newPlatform)
            nextY -= platform.height + platformGap
        }
    }

    private fun generatePlatforms() {
        val factory = getRandomFactory()
        val numberOfPlatforms = Random.nextInt(1, 5)

        for (i in 0 until numberOfPlatforms) {
            val x = Random.nextFloat() * (screenWidth - platform.width)
            val platform = factory.generatePlatform(x, nextY)
            activePlatforms.add(platform)
            nextY -= platform.height + platformGap
        }
    }
}