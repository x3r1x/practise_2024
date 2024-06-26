package com.example.mygame

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import com.example.mygame.`object`.Ball
import com.example.mygame.view.GameView

class MainActivity : Activity(), SensorHandler.SensorCallback {
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var sensorHandler: SensorHandler
    private lateinit var gameView: GameView
    private val handler = Handler(Looper.getMainLooper())
    private val ball = Ball()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получаем размеры экрана
        val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val screenWidth = bounds.width().toFloat()
        val screenHeight = bounds.height().toFloat()

        // Инициализация gameView и установка контента
        gameView = GameView(this, null)
        setContentView(gameView)

        // Инициализация SensorHandler
        sensorHandler = SensorHandler(this, this)

        // Инициализация CollisionHandler
        collisionHandler = CollisionHandler(screenWidth, screenHeight)

        // Запускаем игровой цикл
        startGameLoop()
    }

    private var deltaX = 0f
    private var deltaY = 0f

    private fun startGameLoop() {
        handler.post(object : Runnable {
            override fun run() {
                // Проверяем столкновения с границами экрана
                collisionHandler.checkCollision(ball)

                // Обновляем позицию шара
                ball.updateBallPosition(deltaX, deltaY)

                // Передаем список объектов для отрисовки в GameView
                gameView.drawGame(listOf(ball))

                // Повторяем цикл с задержкой
                handler.postDelayed(this, 16) // 60 fps (1000ms/60 ≈ 16ms)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        sensorHandler.unregister()
    }

    override fun onSensorDataChanged(deltaX: Float, deltaY: Float) {
        this.deltaX = deltaX
        this.deltaY = deltaY
    }
}


