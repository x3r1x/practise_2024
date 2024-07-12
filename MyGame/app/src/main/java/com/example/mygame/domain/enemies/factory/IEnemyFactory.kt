package com.example.mygame.domain.enemies.factory

import android.graphics.BitmapFactory
import com.example.mygame.domain.Enemy

interface IEnemyFactory {
    fun generateEnemy(createdX: Float, createdY: Float) : Enemy

    companion object {
        val BITMAP_OPTION = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}