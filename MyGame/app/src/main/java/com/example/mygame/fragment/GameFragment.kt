package com.example.mygame.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mygame.GameViewModel
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.view.GameView

class GameFragment(private val gameViewModel: GameViewModel) : Fragment() {
    private lateinit var gameView: GameView

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Получаем размеры экрана
        val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val screenWidth = bounds.width().toFloat()
        val screenHeight = bounds.height().toFloat()

        // Инициализация gameView и установка контента
        gameView = GameView(requireContext(), null)
        return gameView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация GameViewModel
        gameViewModel.initialize(gameView.width.toFloat(), gameView.height.toFloat())

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameObjects.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects as List<IDrawable>)
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        gameViewModel.stopGameLoop()
    }
}

