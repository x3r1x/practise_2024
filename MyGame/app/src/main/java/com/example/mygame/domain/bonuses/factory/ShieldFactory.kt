package com.example.mygame.domain.bonuses.factory

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.domain.bonuses.IBonus
import com.example.mygame.domain.bonuses.factory.IBonusFactory.Companion.BITMAP_OPTION
import com.example.mygame.domain.bonuses.factory.IBonusFactory.Companion.COLLECTABLE_OFFSET
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.Shield

class ShieldFactory(resources: Resources) : IBonusFactory {
    private val defaultBitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTION)
    private val onPlayerBitmap = BitmapFactory.decodeResource(resources, ON_PLAYER_IMAGE, BITMAP_OPTION)

    override fun generateBonus(staticPlatform: Platform): IBonus {
        return Shield(defaultBitmap, onPlayerBitmap, staticPlatform.x, staticPlatform.top - DEFAULT_SIDE / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val DEFAULT_SIDE = 100f

        private val IMAGE = R.drawable.shield
        private val ON_PLAYER_IMAGE = R.drawable.shield_on_player
    }
}