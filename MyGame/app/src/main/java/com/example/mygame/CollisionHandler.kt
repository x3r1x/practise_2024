package com.example.mygame

import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform

class CollisionHandler(private val screenWidth: Float, private val screenHeight: Float) {

    private fun checkBallCollision(ball: Ball) {
        val ballBottom = ball.y + ball.radius
        val ballLeft = ball.x
        val ballRight = ball.x
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
            ball.y = 0f + ball.radius
            ball.initialY = ball.y // Обновляем начальную позицию при столкновении с нижней границей экрана
        }
    }

    private fun checkBallAndPlatformCollision(ball: Ball, platforms: List<Platform>) {
        platforms.forEach {
            val ballXOnPlatformX = ball.x + ball.radius >= it.x && ball.x - ball.radius <= it.x + it.width
            val ballYInPlatformY = ball.y + ball.radius >= it.y && ball.y - ball.radius <= it.y + it.height

            if (ballXOnPlatformX && ballYInPlatformY && ball.directionY != Ball.DirectionY.UP) {
                ball.speedY = ball.jumpSpeed
                ball.directionY = Ball.DirectionY.UP
                ball.initialY = ball.y
            }
        }
    }

    fun checkCollisions(ball: Ball, platforms: List<Platform>) {
        checkBallCollision(ball)
        checkBallAndPlatformCollision(ball, platforms)
    }
}
