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

    private val platformGap: Float = 20f

    private val maxVerticalGap = 350f

    private val newPackageHeight = -5000f

    private val factories = listOf(
        staticPlatformFactory,
        disappearingPlatformFactory,
        movingPlatformOnYFactory,
        movingPlatformOnXFactory
    )

    private val platform = Platform(0f, 0f)

    fun generateInitialPlatforms(): MutableList<Platform> {
        val platforms: MutableList<Platform> = mutableListOf()
        while (nextY >= 0) {
            val x = Random.nextFloat() * (screenWidth - platform.width)
            val newPlatform = staticPlatformFactory.generatePlatform(x, nextY)
            platforms.add(newPlatform)
            nextY -= platform.height + platformGap
        }

        return platforms
    }

    fun generatePlatforms(): MutableList<Platform> {
        val startY = nextY
        // TODO: Добавить генерацию сломанных платформ
        // TODO: Они генерируются в дополнение к основным
        val platforms: MutableList<Platform> = mutableListOf()

        while (startY + newPackageHeight < nextY) {
            val factory = getRandomFactory()
            val numberOfPlatforms = Random.nextInt(2, 6)

            for (i in 0 until numberOfPlatforms) {
                var newPlatform: Platform
                var yGap: Float
                var x: Float
                var tries = 0
                val maxTries = 10

                do {
                    x = Random.nextFloat() * (screenWidth - platform.width)
                    yGap = platformGap + Random.nextFloat() * (maxVerticalGap - platformGap)
                    newPlatform = factory.generatePlatform(x, nextY - yGap)
                    tries++
                } while (isOverlapping(platforms, newPlatform) && tries < maxTries)

                if (!isOverlapping(platforms, newPlatform)) {
                    platforms.add(newPlatform)
                    nextY -= platform.height + yGap
                }
            }
        }

        return platforms
    }

    private fun getRandomFactory(): IPlatformFactory {
        // TODO: Сделать шанс генерации на основе score
        return factories[Random.nextInt(factories.size)]
    }

    private fun isOverlapping(platforms: MutableList<Platform>, newPlatform: Platform): Boolean {
        return platforms.any { existingPlatform ->
            val horizontalOverlap = newPlatform.x < existingPlatform.right &&
                                    newPlatform.x + platform.width > existingPlatform.left

            val verticalOverlap = newPlatform.y < existingPlatform.bottom &&
                                  newPlatform.y + platform.height > existingPlatform.top

            horizontalOverlap && verticalOverlap
        }
    }
}