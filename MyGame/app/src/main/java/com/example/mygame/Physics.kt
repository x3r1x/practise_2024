package com.example.mygame

import android.view.Gravity
import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform
import kotlin.math.sqrt

class Physics(private val screenHeight: Float) {
    fun movePlatforms(ball: Ball, platforms: List<Platform>) {
        val whereMove = this.screenHeight - 950f

        if (ball.y <= whereMove && ball.directionY == Ball.DirectionY.DOWN) {
            for (platform in platforms) {
                platform.updatePosition(platform.x, platform.y + ball.speedY)
            }
        }
    }

    fun getStartCollisionSpeed(s: Float, a: Float) : Float {
        return sqrt(2 * s * a)
    }

    fun canMovePlatforms(ball: Ball, platforms: List<Platform>): Boolean {
        return ball.y <= this.screenHeight - 950f && ball.directionY == Ball.DirectionY.UP
    }

    companion object{
        const val GRAVITY = 7.5f
        const val GRAVITY_RATIO = 0.05f
    }
}