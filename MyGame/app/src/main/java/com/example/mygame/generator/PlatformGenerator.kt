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

            // Добавление случайной платформы, если зазор больше 200 пикселей и вероятность 0.1
            if (currentY > -platform.height && Random.nextFloat() < 0.1f) {
                val randomX = Random.nextFloat() * (screenWidth - platform.width)
                val randomY = currentY - (Random.nextFloat() * 400f + 200f) // случайное положение между 200 и 600 пикселями
                platforms.add(Platform(randomX, randomY))
                currentY = randomY - platformGap
            }
        }
    }

    fun generateNextPlatform(): Platform {
        val highestPlatform = Platform(0f, 0f)
        val newY = highestPlatform.y - platformGap
        val newX = Random.nextFloat() * (screenWidth - platform.width)
        val newPlatform = Platform(newX, newY)
        platforms.add(newPlatform)
        return newPlatform
    }

    fun getPlatforms(): List<Platform> {
        return platforms
    }

    fun updatePlatforms(dy: Float) {
        val updatedPlatforms = platforms.map { platform ->
            Platform(platform.x, platform.y + dy)
        }.toMutableList()
        platforms.clear()
        platforms.addAll(updatedPlatforms)
        // Удаляем платформы, которые вышли за нижнюю границу экрана
        platforms.removeAll { it.y > screenHeight }
    }
}