package com.example.mygame

import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform

class CollisionHandler(private val screenWidth: Float, private val screenHeight: Float) {

    private fun checkBallCollision(ball: Ball) {
        val ballBottom = ball.y + ball.radius * 3.6f
        val ballLeft = ball.x - ball.radius
        val ballRight = ball.x + ball.radius
        val ballTop = ball.y - ball.radius

        if (ballLeft < 0f) {
            ball.x = screenWidth - ball.radius
        }

        if (ballRight > screenWidth) {
            ball.x = 0f + ball.radius
        }

        if (ballTop < 0f) {
            ball.y = ball.radius
        }

        if (ballBottom > screenHeight) {
            ball.y = screenHeight - 3.6f * ball.radius
            ball.speedY = 0f
        }
    }

    private fun checkBallAndPlatformCollision(ball: Ball, platforms: List<Platform>) {
        platforms.forEach() {
            val ballXOnPlatformX = ball.x + ball.radius >= it.x && ball.x - ball.radius <= it.x + it.width
            val ballYInPlatformY = ball.y + ball.radius == it.y

            if (ballXOnPlatformX && ballYInPlatformY) {
                ball.speedY = -125f
            }
        }
    }

    fun checkCollisions(ball: Ball, platforms: List<Platform>) {
        checkBallCollision((ball))
        checkBallAndPlatformCollision(ball, platforms)
    }
}