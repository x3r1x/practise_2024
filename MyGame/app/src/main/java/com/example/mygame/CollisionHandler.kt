package com.example.mygame

import android.graphics.Canvas
import com.example.mygame.`interface`.Drawable
import com.example.mygame.`object`.Ball

class Platform : Drawable {
    var x = 0f
    var y = 0f
    val width = 50f
    val height = 10f

    override fun draw(canvas: Canvas) {
        TODO("Not yet implemented")
    }
}

class CollisionHandler(private val screenWidth: Float, private val screenHeight: Float) {

    fun checkBallCollision(ball: Ball) {
        // beyondLeftBoundExit
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
            var ballXOnPlatformX = ball.y + ball.radius == it.x
            var ballYInPlatformY = ball.x >= it.x && ball.x <= (it.x + it.width)

            if (ballXOnPlatformX && ballYInPlatformY) {
                ball.isOnPlatform = true
                ballOnPlatform = true
            }
        }

        if (!ballOnPlatform && ball.isOnPlatform)
        {
            ball.isOnPlatform = false
        }
    }
}