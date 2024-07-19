package com.example.mygame.domain.bonuses.factory

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.domain.bonuses.IBonus
import com.example.mygame.domain.bonuses.factory.IBonusFactory.Companion.BITMAP_OPTION
import com.example.mygame.domain.bonuses.factory.IBonusFactory.Companion.COLLECTABLE_OFFSET
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.Jetpack

class JetpackFactory(resources: Resources) : IBonusFactory {
    private val defaultBitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTION)
    private val bitmapToLeftOfPlayer = BitmapFactory.decodeResource(resources, IMAGE_ON_PLAYER_LEFT, BITMAP_OPTION)
    private val bitmapToRightOfPlayer = BitmapFactory.decodeResource(resources, IMAGE_ON_PLAYER_RIGHT, BITMAP_OPTION)

    override fun generateBonus(staticPlatform: Platform): IBonus {
        return Jetpack(defaultBitmap, bitmapToLeftOfPlayer, bitmapToRightOfPlayer, staticPlatform.x, staticPlatform.top - HEIGHT / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val HEIGHT = 111f

        private val IMAGE = R.drawable.jetpack
        private val IMAGE_ON_PLAYER_RIGHT = R.drawable.player_jetpack_right
        private val IMAGE_ON_PLAYER_LEFT = R.drawable.player_jetpack_left
    }
}