package com.example.mygame.`interface`

import android.graphics.BitmapFactory
import com.example.mygame.`object`.Platform

interface IBonusFactory {

    fun generateBonus(staticPlatform: Platform): IBonus

    companion object {

        const val COLLECTABLE_OFFSET = 25f
        val BITMAP_OPTION = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}