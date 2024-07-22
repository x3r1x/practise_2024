package com.example.mygame.domain.bonus.factory

import android.graphics.BitmapFactory
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonus.IBonus

interface IBonusFactory {
    fun generateBonus(staticPlatform: Platform): IBonus

    companion object {
        const val COLLECTABLE_OFFSET = 25f
        val BITMAP_OPTION = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}