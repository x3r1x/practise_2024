package com.example.mygame.domain.enemies.factory

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.domain.enemies.factory.IEnemyFactory.Companion.BITMAP_OPTION
import com.example.mygame.domain.enemies.Enemy
import com.example.mygame.domain.Screen
import com.example.mygame.domain.enemies.Fly

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