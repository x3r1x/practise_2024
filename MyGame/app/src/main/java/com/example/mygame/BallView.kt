package com.example.mygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BallView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var ballX = 0f
    private var ballY = 0f
    private val ballRadius = 50f
    private val gravity = 10f
    private val ballPaint = Paint().apply {
        color = Color.RED
    }
    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint)
        canvas.drawCircle(ballX, ballY, ballRadius, borderPaint)
    }

    fun updateBallPosition(deltaX: Float) {
        ballX += deltaX
        ballY += gravity

        if (ballX + ballRadius < 0f) {
            ballX = width.toFloat() - ballRadius
        }

        if (ballX + ballRadius > width.toFloat()) {
            ballX = 0f
        }

        if (ballY + ballRadius < 0f) {
            ballY = height.toFloat() - ballRadius
        }

        if (ballY + ballRadius > height.toFloat()) {
            ballY = height.toFloat() - ballRadius
        }

        invalidate()
    }
}