package com.example.mygame.logic

import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform

class CollisionHandler(private val screenWidth: Float, private val screenHeight: Float) {

    private fun checkBallCollision(ball: Ball) {
        val ballLeft = ball.x
        val ballRight = ball.x
        val ballTop = ball.y - ball.radius
        val ballBottom = ball.y + ball.radius

        if (ballLeft < 0f) {
            ball.x = screenWidth - ball.radius
        }

        if (ballRight > screenWidth) {
            ball.x = 0f + ball.radius
        }

        if (ballTop < 0f) {
            ball.y = ball.radius
            ball.directionY = Ball.DirectionY.DOWN
        }

        if (ballBottom > screenHeight) {
            ball.y = 0f + ball.radius
            ball.initialY = ball.y // Обновляем начальную позицию при столкновении с нижней границей экрана
        }
    }

    private fun checkBallAndPlatformCollision(ball: Ball, platforms: List<Platform>) {
        val ballLeft = ball.x - ball.radius
        val ballRight = ball.x + ball.radius
        val ballBottom = ball.y + ball.radius
        platforms.forEach {
            val ballXOnPlatformX = ballRight >= it.left && ballLeft <= it.right
            val ballYInPlatformY = ballBottom >= it.top && ballBottom <= it.bottom

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