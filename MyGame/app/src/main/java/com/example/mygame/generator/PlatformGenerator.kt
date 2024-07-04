package com.example.mygame.generator

import android.content.res.Resources
import com.example.mygame.factories.platforms.BreakingPlatformFactory
import com.example.mygame.factories.DisappearingPlatformFactory
import com.example.mygame.factories.MovingPlatformOnXFactory
import com.example.mygame.factories.MovingPlatformOnYFactory
import com.example.mygame.factories.StaticPlatformFactory
import com.example.mygame.`object`.Platform


class PlatformGenerator(
    var resources: Resources,
    private val screenWidth: Float,
    private val screenHeight: Float
) {
    private val platforms: MutableList<Platform> = mutableListOf()

    private val staticPlatformFactory = StaticPlatformFactory(resources)
    private val breakingPlatformFactory = BreakingPlatformFactory(resources)
    private val disappearingPlatformFactory = DisappearingPlatformFactory(resources)
    private val movingPlatformOnXFactory = MovingPlatformOnXFactory(resources)
    private val movingPlatformOnYFactory = MovingPlatformOnYFactory(resources, screenHeight)

    private val platform = staticPlatformFactory.generatePlatform(0f, 0f)
    private val staticPlatform = staticPlatformFactory.generatePlatform(600f, 1750f)
    private val breakingPlatform = breakingPlatformFactory.generatePlatform(400f, 500f)
    private val disappearingPlatform = disappearingPlatformFactory.generatePlatform(700f, 900f)
    private val movingPlatformOnX = movingPlatformOnXFactory.generatePlatform(300f, 1350f)
    private val movingPlatformOnY = movingPlatformOnYFactory.generatePlatform(700f, 1750f)

    private val platformGap = 20f + platform.height

    init {
        generateInitialPlatforms()
    }

    fun getPlatforms(): MutableList<Platform> {
        return platforms
    }

    fun generateNextPlatform(): Platform {
        TODO("Требуется реализация")
    }

    fun updatePlatforms(dy: Float) {
        TODO("Требуется реализация")
    }

    private fun generateInitialPlatforms() {
        var currentY = screenHeight - platformGap
//        platforms.add(breakingPlatform)
//        platforms.add(disappearingPlatform)
//        platforms.add(movingPlatformOnX)
//        platforms.add(movingPlatformOnY)
        platforms.add(staticPlatform)
        return // delete
//        while (currentY > -platform.height) {
//            val x = Random.nextFloat() * (screenWidth - platform.width)
//            platforms.add(staticPlatformFactory.generatePlatform(x, currentY))
//            currentY -= platformGap
//
//            if (currentY > -platform.height && Random.nextFloat() < 0.1f) {
//                val randomX = Random.nextFloat() * (screenWidth - platform.width)
//                val randomY = currentY - (Random.nextFloat() * 300f + 200f) // случайное положение между 200 и 500 пикселями
//                platforms.add(staticPlatformFactory.generatePlatform(randomX, randomY))
//                currentY = randomY - platformGap
//            }
//        }
    }
}