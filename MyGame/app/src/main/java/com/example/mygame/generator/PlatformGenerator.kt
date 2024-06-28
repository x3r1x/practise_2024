package com.example.mygame.generator

import com.example.mygame.`object`.Platform
import kotlin.random.Random

class PlatformGenerator(
    private val screenWidth: Float,
    val screenHeight: Float,
    private val platformWidth: Float,
    private val platformHeight: Float,
    private val platformGap: Float
) {
    private val platforms: MutableList<Platform> = mutableListOf()

    init {
        // Начальная генерация платформ
        generateInitialPlatforms()
    }

    private fun generateInitialPlatforms() {
        var currentY = screenHeight - platformGap
        while (currentY > screenHeight/2) {
            val x = Random.nextFloat() * (screenWidth - platformWidth)
            platforms.add(Platform(x, currentY))
            currentY -= platformGap
        }
    }

    fun generateNextPlatform(): Platform {
        val highestPlatform = platforms.minByOrNull { it.y } ?: Platform(0f, 0f)
        val newY = highestPlatform.y - platformGap
        val newX = Random.nextFloat() * (screenWidth - platformWidth)
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