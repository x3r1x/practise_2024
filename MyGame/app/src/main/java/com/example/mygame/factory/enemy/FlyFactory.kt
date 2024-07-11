package com.example.mygame.factory.enemy

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IEnemyFactory
import com.example.mygame.`interface`.IEnemyFactory.Companion.BITMAP_OPTION
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.enemies.Fly

class FlyFactory(resources: Resources,
                 private val screen: Screen
) : IEnemyFactory {
    private val bitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTION)

    override fun generateEnemy(createdX: Float, createdY: Float): Enemy {
        return Fly(bitmap, createdX, createdY, screen)
    }

    companion object {
        private val IMAGE = R.drawable.fly
    }
}