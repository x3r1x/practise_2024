package com.example.mygame.domain.bullet

class BulletFactory() {

    fun generateBullet(startX: Float, startY: Float, touchX: Float): Bullet {
        var angle = getAngle(touchX, startX)
        if (angle > 30f) {
            angle = 30f
        } else if (angle < -30f) {
            angle = -30f
        }

        return Bullet(startX, startY, angle)
    }

    private fun getAngle(touchX: Float, startX: Float): Float {
        val angle = (touchX - startX) / 20f

        if (angle > 30f) {
            return 30f
        } else if (angle < -30f) {
            return -30f
        }

        return angle
    }
}