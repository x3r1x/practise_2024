package com.example.mygame

import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform
import kotlin.math.sqrt

class Physics(private val screenHeight: Float) {
    private var platformSpeed = 0f

    fun movePlatforms(ball: Ball, platforms: List<Platform>) {
        var platformOffset = screenHeight - (ball.lastCollision?.bottom ?: 0f) - BOTTOM_POSITION_OF_PLATFORM

        if (platformSpeed == 0f) {
            platformSpeed = platformOffset / (Ball.JUMP_TIME * 2.5f)
        }

        if (platformOffset > 0 && ball.directionY == Ball.DirectionY.UP) {
            for (platform in platforms) {
                platform.updatePosition(platform.x, platform.y + platformSpeed)
            }
            platformOffset -= platformSpeed
        }

        if (platformOffset == 0f) {
            platformSpeed = 0f
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
        const val MAX_JUMP_PIXELS_FROM_BOTTOM = 1100f
        const val BOTTOM_POSITION_OF_PLATFORM = 285f
    }
}