package com.example.mygame.factory.bonus

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IBonus
import com.example.mygame.`interface`.IBonusFactory
import com.example.mygame.`interface`.IBonusFactory.Companion.BITMAP_OPTION
import com.example.mygame.`interface`.IBonusFactory.Companion.COLLECTABLE_OFFSET
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.bonuses.Jetpack

class JetpackFactory(resources: Resources) : IBonusFactory {
    private val defaultBitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTION)
    private val leftOnPlayerBitmap = BitmapFactory.decodeResource(resources, IMAGE_ON_PLAYER_LEFT, BITMAP_OPTION)
    private val rightOnPlayerBitmap = BitmapFactory.decodeResource(resources, IMAGE_ON_PLAYER_RIGHT, BITMAP_OPTION)

    override fun generateBonus(staticPlatform: Platform): IBonus {
        return Jetpack(defaultBitmap, leftOnPlayerBitmap, rightOnPlayerBitmap, staticPlatform.x, staticPlatform.top - HEIGHT / 2 - COLLECTABLE_OFFSET)
    }

    companion object {
        private const val HEIGHT = 111f

        private val IMAGE = R.drawable.jetpack
        private val IMAGE_ON_PLAYER_LEFT = R.drawable.player_jetpack_left
        private val IMAGE_ON_PLAYER_RIGHT = R.drawable.player_jetpack_right
    }
}