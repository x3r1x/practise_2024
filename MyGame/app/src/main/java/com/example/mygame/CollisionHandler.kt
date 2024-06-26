package com.example.mygame

import android.util.Log
import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform

class CollisionHandler(private val screenWidth: Float, private val screenHeight: Float) {

    fun checkBallCollision(ball: Ball) {
        val beyondLeftBoundExit = ball.x - ball.radius < 0
        val beyondRightBoundExit = ball.x + ball.radius > screenWidth
        val beyondTopBoundExit = ball.y - ball.radius < 0
        val beyondBottomBoundExit = ball.y + ball.radius > screenHeight

        if (beyondLeftBoundExit) {
            ball.x = screenWidth - ball.radius
        }
        if (beyondRightBoundExit) {
            ball.x = 0f + ball.radius
        }
        if (beyondTopBoundExit) {
            ball.y = ball.radius
        }
        if (beyondBottomBoundExit) {
            ball.y = 0f + ball.radius
        }
    }

    fun checkBallAndPlatformCollision(ball: Ball, platforms: List<Platform>) {
        var ballOnPlatform = false

        platforms.forEach() {
            val ballXOnPlatformX = ball.x + ball.radius / 2 >= it.x && ball.x - ball.radius / 2 <= (it.x + it.width)
            val ballYInPlatformY = ball.y + ball.radius == it.y

            if (ballXOnPlatformX && ballYInPlatformY) {
                ball.isOnPlatform = true
                ballOnPlatform = true
                ball.speedY = -120f
            }
        }

        if (!ballOnPlatform && ball.isOnPlatform)
        {
            ball.isOnPlatform = false
        }
    }
}