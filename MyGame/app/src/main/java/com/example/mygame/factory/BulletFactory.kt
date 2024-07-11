package com.example.mygame.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`object`.Bullet
import com.example.mygame.`object`.Player

class BulletFactory(resources: Resources) {
    private val image: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet)

    fun generateBullet() : Bullet {
        return Bullet(image)
    }
}