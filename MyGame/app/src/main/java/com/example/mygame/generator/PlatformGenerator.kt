package com.example.mygame.generator

import com.example.mygame.`object`.Platform
import kotlin.random.Random

class PlatformGenerator(
    private val screenWidth: Float,
    private val screenHeight: Float,
) {
    private val platforms: MutableList<Platform> = mutableListOf()

    private val platform: Platform = Platform(0f,0f)
    private val platformGap = 20f + platform.height

    init {
        generateInitialPlatforms()
    }

    private fun generateInitialPlatforms() {
        var currentY = screenHeight - platformGap
        while (currentY > -platform.height) {
            val x = Random.nextFloat() * (screenWidth - platform.width)
            platforms.add(Platform(x, currentY))
            currentY -= platformGap

            if (currentY > -platform.height && Random.nextFloat() < 0.1f) {
                val randomX = Random.nextFloat() * (screenWidth - platform.width)
                val randomY = currentY - (Random.nextFloat() * 300f + 200f) // случайное положение между 200 и 500 пикселями
                platforms.add(Platform(randomX, randomY))
                currentY = randomY - platformGap
            }
        }
    }

    fun getPlatforms(): List<Platform> {
        return platforms
    }

    fun generateNextPlatform(): Platform {
        TODO("Требуется реализация")
    }

    fun updatePlatforms(dy: Float) {
        TODO("Требуется реализация")
    }
}