package com.example.mygame.domain.bonus.factory

import com.example.mygame.domain.platform.Platform
import com.example.mygame.domain.bonus.IBonus
import com.example.mygame.domain.bonus.Shield
import com.example.mygame.domain.bonus.factory.IBonusFactory.Companion.COLLECTABLE_OFFSET

class ShieldFactory : IBonusFactory {
    override fun generateBonus(staticPlatform: Platform): IBonus {
        return Shield(staticPlatform.x, staticPlatform.top - DEFAULT_SIDE / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val DEFAULT_SIDE = 100f
    }
}