package com.example.mygame.`object`

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.`interface`.Drawable

class Ball : Drawable {
    val radius = 50f
    private val gravity = 10f
    private val speed = 5f

    private val ballPaint = Paint().apply {
        color = Color.RED
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    var x = 0f;
    var y = 0f;

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, ballPaint)
        canvas.drawCircle(x, y, radius, borderPaint)
    }

    fun updateBallPosition(deltaX: Float, deltaY: Float) {
        x += deltaX * speed
        y += gravity

//        if (ballX < 0f) {
//            //левая границы
//            ballX = width.toFloat() - ballRadius
//        }
//
//        if (ballX + ballRadius > width.toFloat()) {
//            //правая граница
//            ballX = 0f
//        }
//
//        if (ballY + ballRadius < 0f) {
//            //верхняя граница
//            ballY = height.toFloat() - ballRadius
//        }
//
//        if (ballY + ballRadius > height.toFloat()) {
//            //нижняя граница
//            ballY = 0f
//        }
//
//        invalidate()
    }
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//        // Устанавливаем начальную позицию шара
//        ballX = w / 2f
//        ballY = ballRadius
//    }
}