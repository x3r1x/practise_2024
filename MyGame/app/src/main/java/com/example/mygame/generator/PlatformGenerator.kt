package com.example.mygame.generator

import kotlin.math.abs
import kotlin.random.Random
import android.content.res.Resources
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.Platform
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.factory.platform.StaticPlatformFactory
import com.example.mygame.factory.platform.BreakingPlatformFactory
import com.example.mygame.factory.platform.MovingPlatformOnXFactory
import com.example.mygame.factory.platform.MovingPlatformOnYFactory
import com.example.mygame.factory.platform.DisappearingPlatformFactory

class PlatformGenerator(
    resources: Resources,
    private val screen: Screen
) {
    private val staticPlatformFactory = StaticPlatformFactory(resources)
    private val breakingPlatformFactory = BreakingPlatformFactory(resources)
    private val movingPlatformOnXFactory = MovingPlatformOnXFactory(resources)
    private val disappearingPlatformFactory = DisappearingPlatformFactory(resources)

    private val movingPlatformOnYFactory = MovingPlatformOnYFactory(resources)

    private val platformGap: Float = 50f

    private val maxVerticalGap = 350f

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
            val x = Random.nextFloat() * (screen.width - platform.width) + 100f
            val newPlatform = staticPlatformFactory.generatePlatform(x, newY)
            platforms.add(newPlatform)
            newY -= platform.height + platformGap
        }

        return platforms
    }

    fun generatePlatforms(from: Float): MutableList<Platform> {

        // TODO: Добавить генерацию сломанных платформ
        // TODO: Они генерируются в дополнение к основным
        val platforms: MutableList<Platform> = mutableListOf()
        var newY = from

        while (abs(newY) < newPackageHeight - from) {
            val factory = getRandomFactory()
            val numberOfPlatforms = Random.nextInt(2, 6)
            val platformsPack: MutableList<Platform> = mutableListOf()

            for (i in 0 until numberOfPlatforms) {
                var newPlatform: Platform
                var yGap: Float
                var x: Float

                do {
                    x = Random.nextFloat() * (screen.width - platform.width) + 100f
                    yGap = platformGap + Random.nextFloat() * (maxVerticalGap - platformGap)
                    newPlatform = factory.generatePlatform(x, newY - yGap)
                } while (isOverlapping(platformsPack, newPlatform))

                platformsPack.add(newPlatform)

                newY -= platform.height + yGap
            }

            platforms.addAll(platformsPack)
        }

        return platforms
    }

    fun generatePack(from: Float): MutableList<Platform> {
        var newY = from
        val factory = getRandomFactory()
        val numberOfPlatforms = Random.nextInt(2, 6)
        val platformsPack: MutableList<Platform> = mutableListOf()

        for (i in 0 until numberOfPlatforms) {
            var newPlatform: Platform
            var yGap: Float
            var x: Float

            do {
                x = Random.nextFloat() * (screen.width - platform.width) + 100f
                yGap = platformGap + Random.nextFloat() * (maxVerticalGap - platformGap)
                newPlatform = factory.generatePlatform(x, newY - yGap)
            } while (isOverlapping(platformsPack, newPlatform))

            platformsPack.add(newPlatform)

            newY -= platform.height + yGap
        }

        return platformsPack
    }

    fun generatePlatform(from: Float): Platform {
        val factory = getRandomFactory()
        val x = Random.nextFloat() * (screen.width - platform.width) + 100f
        val y = from - Random.nextFloat() * (maxVerticalGap - platformGap)

        return factory.generatePlatform(x, y)
    }

    private fun getRandomFactory(): IPlatformFactory {
        // TODO: Сделать шанс генерации на основе score
        return factories[Random.nextInt(factories.size)]
    }

    private fun isOverlapping(platforms: MutableList<Platform>, newPlatform: Platform): Boolean {
        return platforms.any { existingPlatform ->
            val horizontalOverlap = newPlatform.left < existingPlatform.right &&
                                    newPlatform.right > existingPlatform.left

            val verticalOverlap = newPlatform.top < existingPlatform.bottom &&
                                  newPlatform.bottom > existingPlatform.top

            val outOfScreen = newPlatform.left < screen.left || newPlatform.right > screen.right

            horizontalOverlap && verticalOverlap && outOfScreen
        }
    }
}