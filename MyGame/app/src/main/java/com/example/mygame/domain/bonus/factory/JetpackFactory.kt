package com.example.mygame.domain.bonus.factory

import com.example.mygame.domain.platform.Platform
import com.example.mygame.domain.bonus.IBonus
import com.example.mygame.domain.bonus.Jetpack
import com.example.mygame.domain.bonus.factory.IBonusFactory.Companion.COLLECTABLE_OFFSET

class JetpackFactory : IBonusFactory {
    override fun generateBonus(staticPlatform: Platform): IBonus {
        return Jetpack(staticPlatform.x, staticPlatform.top - HEIGHT / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val HEIGHT = 111f
    }
}