package com.example.mygame.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`object`.Bullet

class BulletFactory(resources: Resources) {
    private val image: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet)

    fun generateBullet(startX: Float, startY: Float): Bullet {
        return Bullet(image, startX, startY)
    }
}