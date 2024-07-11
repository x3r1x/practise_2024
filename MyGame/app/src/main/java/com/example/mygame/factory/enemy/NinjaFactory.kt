package com.example.mygame.factory.enemy

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IEnemyFactory
import com.example.mygame.`interface`.IEnemyFactory.Companion.BITMAP_OPTION
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.enemies.Ninja

class NinjaFactory(resources: Resources,
                   private val screen: Screen
) : IEnemyFactory {
    private val bimap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTION)

    override fun generateEnemy(createdX: Float, createdY: Float): Enemy {
        return Ninja(bimap, createdX, createdY, screen)
    }

    companion object {
        private val IMAGE = R.drawable.ninja
    }
}