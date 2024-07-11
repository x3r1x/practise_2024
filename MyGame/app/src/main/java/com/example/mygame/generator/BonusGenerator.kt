package com.example.mygame.generator

import kotlin.random.Random
import android.content.res.Resources
import com.example.mygame.`interface`.IBonusFactory
import com.example.mygame.factory.bonus.ShieldFactory
import com.example.mygame.factory.bonus.SpringFactory
import com.example.mygame.factory.bonus.JetpackFactory
import com.example.mygame.`interface`.IBonus
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.platform.StaticPlatform

class BonusGenerator(resources: Resources) {
    private val shieldFactory = ShieldFactory(resources)
    private val springFactory = SpringFactory(resources)
    private val jetpackFactory = JetpackFactory(resources)

    private val factories = listOf(
        shieldFactory,
        springFactory,
        jetpackFactory
    )

    fun generateBonus(platform: Platform): IBonus? {
        if (platform::class != StaticPlatform::class) {
            return null
        }

        var bonus: IBonus? = null
        val random = Random.nextFloat()
        when {
            random < 0.02f -> {
                val jetpack = jetpackFactory.generateBonus(platform)
                bonus = jetpack
            }

            random < 0.08f -> {
                val shield = shieldFactory.generateBonus(platform)
                bonus = shield
            }

            random < 0.25f -> {
                val spring = springFactory.generateBonus(platform)
                bonus = spring
            }
        }

        return bonus
    }

    private fun getRandomFactory(): IBonusFactory {
        // TODO: Сделать шанс генерации на основе score
        return factories[Random.nextInt(factories.size)]
    }
}