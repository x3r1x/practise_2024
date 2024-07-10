package com.example.mygame.`interface`

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.platform.StaticPlatform

interface IBonusFactory {
    fun generateBonus(platform: Platform) : IGameObject

    companion object {
        const val COLLECTABLE_OFFSET = 25f

        val BITMAP_OPTION = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
    fun generateBonus(staticPlatform: StaticPlatform)
}