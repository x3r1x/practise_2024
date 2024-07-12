package com.example.mygame.domain.bullet

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R

class BulletFactory(resources: Resources) {
    private val image: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet)

    fun generateBullet(startX: Float, startY: Float): Bullet {
        return Bullet(image, startX, startY)
    }
}