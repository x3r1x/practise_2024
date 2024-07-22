package com.example.mygame.domain.bonus.factory

import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonus.IBonus
import com.example.mygame.domain.bonus.Spring

class SpringFactory : IBonusFactory {

    override fun generateBonus(staticPlatform: Platform): IBonus {
        val randomPosition = (GameConstants.MIN_SPRING_SPAWN_X ..GameConstants.MAX_SPRING_SPAWN_X).random()

        return Spring(staticPlatform.left + randomPosition, staticPlatform.top - HEIGHT / 2)
    }

    companion object {
        private const val HEIGHT = 53f
    }
}