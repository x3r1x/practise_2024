package com.example.mygame.factories.bonuses

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IBonusFactory
import com.example.mygame.`interface`.IBonusFactory.Companion.BITMAP_OPTION
import com.example.mygame.`interface`.IBonusFactory.Companion.COLLECTABLE_OFFSET
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.bonuses.Shield

class ShieldFactory(resources: Resources) : IBonusFactory {
    private val defaultBitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTION)
    private val onPlayerBitmap = BitmapFactory.decodeResource(resources, ON_PLAYER_IMAGE, BITMAP_OPTION)

    override fun generateBonus(platform: Platform): IGameObject {
        return Shield(defaultBitmap, onPlayerBitmap, platform.x, platform.top - DEFAULT_SIDE / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val DEFAULT_SIDE = 100f

        private val IMAGE = R.drawable.shield
        private val ON_PLAYER_IMAGE = R.drawable.shield_on_player
    }
}