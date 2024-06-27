package com.example.mygame

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform
import com.example.mygame.view.GameView

class MainActivity : Activity(), SensorHandler.SensorCallback {
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var positionHandler: PositionHandler
    private lateinit var sensorHandler: SensorHandler
    private lateinit var gameView: GameView
    private val handler = Handler(Looper.getMainLooper())

    private val platform1 = Platform()
    private val platform2 = Platform()
    private val platform3 = Platform()
    private val platforms = listOf(platform1, platform2, platform3)

    private val ball = Ball()

    private var deltaX = 0f
    private var deltaY = 0f

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Получаем размеры экрана
        val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val screenWidth = bounds.width().toFloat()
        val screenHeight = bounds.height().toFloat()
        platform1.setPosition(100f, 950f)
        platform2.setPosition(600f, 1150f)
        platform3.setPosition(200f, 1650f)

        ball.setPosition(500f, 500f)

        // Инициализация gameView и установка контента
        gameView = GameView(this, null)
        setContentView(gameView)

        // Инициализация SensorHandler
        sensorHandler = SensorHandler(this, this)

        // Инициализация CollisionHandler
        collisionHandler = CollisionHandler(screenWidth, screenHeight)

        //Инициализация PositionHandler
        positionHandler = PositionHandler(deltaX, deltaY)

        // Запускаем игровой цикл
        startGameLoop()
    }

    private fun startGameLoop() {
        handler.post(object : Runnable {
            override fun run() {
                // Обновляем позицию шара
                positionHandler.updateCoords(deltaX, deltaY)

                // Проверяем коллизии
                collisionHandler.checkCollisions(ball, platforms)

                positionHandler.updatePositions(listOf(ball) + platforms)

                // Передаем список объектов для отрисовки в GameView
                gameView.drawGame(listOf(platform1, platform2, platform3, ball))

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