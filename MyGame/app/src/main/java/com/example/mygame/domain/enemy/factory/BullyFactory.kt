package com.example.mygame.domain.enemy.factory

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.domain.enemy.factory.IEnemyFactory.Companion.BITMAP_OPTION
import com.example.mygame.domain.enemy.Enemy
import com.example.mygame.domain.enemy.Bully

class BullyFactory(resources: Resources) : IEnemyFactory {
    private val bitmap = BitmapFactory.decodeResource(resources, IMAGE, BITMAP_OPTION)

    override fun generateEnemy(createdX: Float, createdY: Float): Enemy {
        return Bully(bitmap, createdX, createdY)
    }

    companion object {
        private val IMAGE = R.drawable.bully
    }
}