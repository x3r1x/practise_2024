package com.example.mygame.generator

import android.content.res.Resources
import com.example.mygame.factories.platforms.BreakingPlatformFactory
import com.example.mygame.factories.platforms.DisappearingPlatformFactory
import com.example.mygame.factories.platforms.MovingPlatformOnXFactory
import com.example.mygame.factories.platforms.MovingPlatformOnYFactory
import com.example.mygame.factories.platforms.StaticPlatformFactory
import com.example.mygame.`object`.Platform
import kotlin.random.Random


class PlatformGenerator(
    var resources: Resources,
    private val screenWidth: Float,
    screenHeight: Float
) {
    private var currentY = screenHeight

    private val platform = Platform(0f,0f)

    private val platformGap = 20f + platform.height

    private val platforms: MutableList<Platform> = mutableListOf()

    private val staticPlatformFactory = StaticPlatformFactory(resources)
    private val breakingPlatformFactory = BreakingPlatformFactory(resources)
    private val disappearingPlatformFactory = DisappearingPlatformFactory(resources)
    private val movingPlatformOnXFactory = MovingPlatformOnXFactory(resources)

    private val movingPlatformOnYFactory = MovingPlatformOnYFactory(resources)

    private val factories = listOf(
        staticPlatformFactory,
        disappearingPlatformFactory,
        movingPlatformOnYFactory,
        movingPlatformOnXFactory
    )

    init {
        generateInitialPlatforms()
    }

    fun getPlatforms(): MutableList<Platform> {
        return platforms
    }

    fun generateNextPlatform() {
        val randomY = currentY - (Random.nextFloat() * 200f + 200f) // случайное положение между 200 и 400 пикселями
        currentY = randomY - platformGap
        val platform = factories.random().generatePlatform(
            Random.nextFloat() * (screenWidth - 350f) + 300f,
                    randomY
        )

        platforms += platform
    }

    fun updatePlatforms(dy: Float) {
        TODO("Требуется реализация")
    }

    private fun generateInitialPlatforms() {

        while (currentY > -platform.height) {
            val x = Random.nextFloat() * (screenWidth - platform.width)
            platforms.add(staticPlatformFactory.generatePlatform(x, currentY))
            currentY -= platformGap

            if (currentY > -platform.height && Random.nextFloat() < 0.1f) {
                val randomX = Random.nextFloat() * (screenWidth - platform.width)
                val randomY = currentY - (Random.nextFloat() * 300f + 200f) // случайное положение между 200 и 500 пикселями
                platforms.add(staticPlatformFactory.generatePlatform(randomX, randomY))
                currentY = randomY - platformGap
            }
        }
    }
}