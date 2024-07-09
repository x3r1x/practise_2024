package com.example.mygame.factories.bonuses

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IBonusFactory
import com.example.mygame.`interface`.IBonusFactory.Companion.BITMAP_OPTION
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.bonuses.Spring

class SpringFactory(resources: Resources) : IBonusFactory {
    private val firstStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[0], BITMAP_OPTION)
    private val secondStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[1], BITMAP_OPTION)
    private val thirdStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[2], BITMAP_OPTION)
    private val fourthStateBitmap = BitmapFactory.decodeResource(resources, SPRING_IMAGES[3], BITMAP_OPTION)
    private val bitmaps = mutableListOf(firstStateBitmap, secondStateBitmap, thirdStateBitmap, fourthStateBitmap)

    override fun generateBonus(platform: Platform): IGameObject {
        val randomPosition = (MIN_SPRING_SPAWN_X..MAX_SPRING_SPAWN_X).random()

        return Spring(bitmaps, platform.left + randomPosition, platform.top)
    }

    companion object {
        private const val MIN_SPRING_SPAWN_X = 40
        private const val MAX_SPRING_SPAWN_X = 180

        private val SPRING_IMAGES = listOf(
            R.drawable.spring_state_1,
            R.drawable.spring_state_2,
            R.drawable.spring_state_3,
            R.drawable.spring_state_4
        )
    }
}