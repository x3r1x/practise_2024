package com.example.mygame.generator

import kotlin.random.Random
import android.content.res.Resources
import com.example.mygame.`interface`.IBonusFactory
import com.example.mygame.factory.bonus.ShieldFactory
import com.example.mygame.factory.bonus.SpringFactory
import com.example.mygame.factory.bonus.JetpackFactory
import com.example.mygame.`interface`.IBonus
import com.example.mygame.`object`.Platform

class BonusGenerator(resources: Resources) {
    private val shieldFactory = ShieldFactory(resources)
    private val springFactory = SpringFactory(resources)
    private val jetpackFactory = JetpackFactory(resources)

    private val factories = listOf(
        shieldFactory,
        springFactory,
        jetpackFactory
    )

    fun generateBonuses(platforms: List<Platform>): MutableList<IBonus> {
        val bonuses: MutableList<IBonus> = mutableListOf()

        platforms.forEach {
            val random = Random.nextFloat()
            when {
                random < 0.1f-> {
                    val jetpack = jetpackFactory.generateBonus(it)
                    bonuses.add(jetpack)
                }
                random < 0.15f -> {
                    val shield = shieldFactory.generateBonus(it)
                    bonuses.add(shield)
                }
                random < 0.25f -> {
                    val spring = springFactory.generateBonus(it)
                    bonuses.add(spring)
                }
            }
        }

        return bonuses
    }

    private fun getRandomFactory(): IBonusFactory {
        // TODO: Сделать шанс генерации на основе score
        return factories[Random.nextInt(factories.size)]
    }
}