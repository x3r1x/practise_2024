package com.example.mygame.generator

import kotlin.random.Random
import android.content.res.Resources
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Platform
import com.example.mygame.`interface`.IBonus
import com.example.mygame.`interface`.IBonusFactory
import com.example.mygame.`object`.interactable.Shield
import com.example.mygame.`object`.interactable.Spring
import com.example.mygame.`object`.interactable.Jetpack
import com.example.mygame.factories.bonuses.ShieldFactory
import com.example.mygame.factories.bonuses.SpringFactory
import com.example.mygame.factories.bonuses.JetpackFactory

class BonusGenerator(private val resources: Resources, private val player: Player) {
    private val shieldFactory = ShieldFactory()
    private val springFactory = SpringFactory()
    private val jetpackFactory = JetpackFactory()

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
                    val jetpack = Jetpack(resources, player).apply { createOnPlatform(it) }
                    bonuses.add(jetpack)
                }
                random < 0.15f -> {
                    val shield = Shield(resources, player).apply { createOnPlatform(it) }
                    bonuses.add(shield)
                }
                random < 0.25f -> {
                    val spring = Spring(resources).apply { createOnPlatform(it) }
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