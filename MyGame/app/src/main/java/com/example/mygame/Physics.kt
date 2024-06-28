package com.example.mygame

import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform
import kotlin.math.sqrt

class Physics(screenH: Float) {
    private val screenHeigth = screenH

    fun movePlatforms(ball: Ball, platforms: List<Platform>) {
        val whenToMove = screenHeigth - 950f

        if (ball.y <= whenToMove && ball.directionY == Ball.DirectionY.DOWN) {
            for (platform in platforms) {
                platform.setPosition(platform.x, platform.y + ball.speedY)
            }
        }
    }

    fun getStartCollisionSpeed(S: Float, a: Float) : Float {
        return sqrt(2 * S * a)
    }
}