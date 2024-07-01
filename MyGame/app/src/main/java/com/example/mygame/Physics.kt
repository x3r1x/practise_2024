package com.example.mygame

import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform

class Physics(private val screenHeight: Float) {
    fun movePlatforms(ball: Ball, platforms: List<Platform>) {
        val whereMove = this.screenHeight - 950f

        if (ball.y <= whereMove && ball.directionY == Ball.DirectionY.UP) {
            for (platform in platforms) {
                platform.updatePosition(platform.x, platform.y + ball.speedY)
            }
        }
    }

    fun getStartCollisionSpeed(S: Float, t: Float) : Float {
        return 2 * S / t
    }

    fun getJumpBoost(U: Float, t: Float) : Float {
        return U / t
    }

    companion object {
        const val GRAVITY = 7.5f
        const val GRAVITY_RATIO = 0.01f

        const val MAX_JUMP_HEIGHT = 600f
        const val MAX_JUMP_PIXELS_FROM_BOTTOM = 1100f
    }
}