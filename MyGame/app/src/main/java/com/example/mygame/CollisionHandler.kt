package com.example.mygame

import com.example.mygame.`object`.Ball

class CollisionHandler(private val screenWidth: Float, private val screenHeight: Float) {

    fun checkCollision(ball: Ball) {
        if (ball.ballX - ball.radius < 0) {
            ball.ballX = screenWidth - ball.radius
        }
        if (ball.ballX + ball.radius > screenWidth) {
            ball.ballX = 0f + ball.radius
        }
        if (ball.ballY - ball.radius < 0) {
            ball.ballY = ball.radius
        }
        if (ball.ballY + ball.radius > screenHeight) {
            ball.ballY = 0f + ball.radius
        }
    }
}