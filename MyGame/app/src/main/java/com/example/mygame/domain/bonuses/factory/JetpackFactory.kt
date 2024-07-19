package com.example.mygame.domain.bonuses.factory

import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.IBonus
import com.example.mygame.domain.bonuses.Jetpack
import com.example.mygame.domain.bonuses.factory.IBonusFactory.Companion.COLLECTABLE_OFFSET

class JetpackFactory : IBonusFactory {
    override fun generateBonus(staticPlatform: Platform): IBonus {
        return Jetpack(staticPlatform.x, staticPlatform.top - HEIGHT / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val HEIGHT = 111f
    }
}