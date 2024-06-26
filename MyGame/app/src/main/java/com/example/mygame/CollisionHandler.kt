package com.example.mygame

import com.example.mygame.`object`.Ball

class CollisionHandler(private val screenWidth: Float, private val screenHeight: Float) {

    fun checkCollision(ball: Ball) {
        if (ball.x - ball.radius < 0) {
            ball.x = screenWidth - ball.radius
        }
        if (ball.x + ball.radius > screenWidth) {
            ball.x = 0f + ball.radius
        }
        if (ball.y - ball.radius < 0) {
            ball.y = ball.radius
        }
        if (ball.y + ball.radius > screenHeight) {
            ball.y = 0f + ball.radius
        }
    }
}