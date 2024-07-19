package com.example.mygame.domain.bonuses.factory

import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.IBonus
import com.example.mygame.domain.bonuses.Shield
import com.example.mygame.domain.bonuses.factory.IBonusFactory.Companion.COLLECTABLE_OFFSET

class ShieldFactory : IBonusFactory {
    override fun generateBonus(staticPlatform: Platform): IBonus {
        return Shield(staticPlatform.x, staticPlatform.top - DEFAULT_SIDE / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val DEFAULT_SIDE = 100f
    }
}