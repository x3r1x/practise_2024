package com.example.mygame.domain.bullet

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.mygame.R

class BulletFactory(resources: Resources) {
    private val image: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.bullet)

    fun generateBullet(startX: Float, startY: Float, touchX: Float, touchY: Float): Bullet {
        var angle = getAngle(touchX, startX)
        if (angle > 30f) {
            angle = 30f
        } else if (angle < -30f) {
            angle = -30f
        }
        Log.i("shoot angle", "$angle")
        return Bullet(image, startX, startY, angle)
    }

    private fun getAngle(touchX: Float, startX: Float) : Float {
        val angle = (touchX - startX) / 20f

        if (angle > 30f) {
            return 30f
        } else if (angle < -30f) {
            return -30f
        }

        return angle
    }
}