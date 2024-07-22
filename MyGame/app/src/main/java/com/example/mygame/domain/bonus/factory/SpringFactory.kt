package com.example.mygame.domain.bonus.factory

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.bonus.IBonus
import com.example.mygame.domain.bonus.factory.IBonusFactory.Companion.BITMAP_OPTION
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonus.Spring

class SpringFactory(resources: Resources) : IBonusFactory {
    private val firstStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[0], BITMAP_OPTION)
    private val secondStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[1], BITMAP_OPTION)
    private val thirdStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[2], BITMAP_OPTION)
    private val fourthStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[3], BITMAP_OPTION)
    private val bitmaps = mutableListOf(firstStateBitmap, secondStateBitmap, thirdStateBitmap, fourthStateBitmap)

    override fun generateBonus(staticPlatform: Platform): IBonus {
        val randomPosition = (GameConstants.MIN_SPRING_SPAWN_X ..GameConstants.MAX_SPRING_SPAWN_X).random()

        return Spring(bitmaps, staticPlatform.left + randomPosition, staticPlatform.top - HEIGHT / 2)
    }

    companion object {
        private const val HEIGHT = 53f

        private val SPRING_IMAGES = listOf(
            R.drawable.spring_state_1,
            R.drawable.spring_state_2,
            R.drawable.spring_state_3,
            R.drawable.spring_state_4
        )
    }
}