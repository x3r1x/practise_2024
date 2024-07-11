package com.example.mygame.`interface`

import android.graphics.BitmapFactory
import com.example.mygame.`object`.Enemy

interface IEnemyFactory {
    fun generateEnemy(createdX: Float, createdY: Float) : Enemy

    companion object {
        val BITMAP_OPTION = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}