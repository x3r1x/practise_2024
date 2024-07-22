package com.example.mygame.domain.enemy.factory

import android.graphics.BitmapFactory
import com.example.mygame.domain.enemy.Enemy

interface IEnemyFactory {
    fun generateEnemy(createdX: Float, createdY: Float) : Enemy

    companion object {
        val BITMAP_OPTION = BitmapFactory.Options().apply {
            inScaled = false
        }
    }
}