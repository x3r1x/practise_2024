package com.example.mygame

import GameViewModel
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.mygame.view.GameView

class MainActivity : ComponentActivity() {
    private lateinit var gameView: GameView
    private val gameViewModel: GameViewModel by viewModels()

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

        // Инициализация GameViewModel
        gameViewModel.initialize(screenWidth, screenHeight)

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameObjects.observe(this, Observer { gameObjects ->
            gameView.drawGame(gameObjects)
        })

        // Запускаем игровой цикл через ViewModel
        startGameLoop()
    }

    private fun startGameLoop() {
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                // Передаем обновленные данные для ViewModel
                gameViewModel.updateDelta(gameViewModel.deltaX, gameViewModel.deltaY)

                // Повторяем цикл с задержкой
                Handler(Looper.getMainLooper()).postDelayed(this, 16) // 60 fps (1000ms/60 ≈ 16ms)
            }
        })
    }

    override fun onPause() {
        super.onPause()
    }
}
