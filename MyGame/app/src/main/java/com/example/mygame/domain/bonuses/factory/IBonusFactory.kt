package com.example.mygame.domain.bonuses.factory

import android.graphics.BitmapFactory
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.IBonus

interface IBonusFactory {
    fun generateBonus(staticPlatform: Platform): IBonus

    companion object {
        const val COLLECTABLE_OFFSET = 25f
        val BITMAP_OPTION = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}