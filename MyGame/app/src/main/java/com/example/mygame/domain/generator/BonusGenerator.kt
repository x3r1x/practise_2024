package com.example.mygame.domain.generator

import kotlin.random.Random
import android.content.res.Resources
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.bonus.factory.ShieldFactory
import com.example.mygame.domain.bonus.factory.SpringFactory
import com.example.mygame.domain.bonus.factory.JetpackFactory
import com.example.mygame.domain.bonus.IBonus
import com.example.mygame.domain.Platform
import com.example.mygame.domain.platform.StaticPlatform

class BonusGenerator(resources: Resources) {
    private val shieldFactory = ShieldFactory(resources)
    private val springFactory = SpringFactory(resources)
    private val jetpackFactory = JetpackFactory(resources)

    fun generateBonus(platform: Platform): IBonus? {
        if (platform::class != StaticPlatform::class) {
            return null
        }

        var bonus: IBonus? = null

        val random = Random.nextFloat()

        when {
            random < GameConstants.JETPACK_SPAWN_CHANCE -> {
                val jetpack = jetpackFactory.generateBonus(platform)
                bonus = jetpack
            }
            random < GameConstants.SHIELD_SPAWN_CHANCE -> {
                val shield = shieldFactory.generateBonus(platform)
                bonus = shield
            }
            random < GameConstants.SPRING_SPAWN_CHANCE -> {
                val spring = springFactory.generateBonus(platform)
                bonus = spring
            }
        }

        return bonus
    }
}