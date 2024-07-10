package com.example.mygame

import android.R
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.annotation.RequiresApi
import com.example.mygame.view.GameView
import androidx.activity.ComponentActivity
import androidx.core.view.WindowInsetsCompat
import com.example.mygame.`interface`.IDrawable
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var gameView: GameView

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,
            window.decorView.findViewById(R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

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
        gameViewModel.gameObjects.observe(this) { gameObjects ->
            gameView.drawGame(gameObjects as List<IDrawable>)
        }

        //запрещаем выключение экрана и прячем UI системы
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        hideSystemUI()

        // Запускаем игровой цикл через ViewModel
        gameViewModel.startGameLoop()
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.registerSensorHandler()
    }

    override fun onPause() {
        super.onPause()
        gameViewModel.unregisterSensorHandler()
    }
}