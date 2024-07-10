package com.example.mygame.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mygame.GameViewModel
import com.example.mygame.R
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.Player
import com.example.mygame.view.GameView

class GameFragment : Fragment() {
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var gameView: GameView

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Получаем размеры экрана
        val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val screenWidth = bounds.width().toFloat()
        val screenHeight = bounds.height().toFloat()

        // Инициализация GameViewModel
        gameViewModel.initialize(screenWidth, screenHeight)

        // Инициализация gameView и установка контента
        gameView = GameView(requireContext(), null)
        return gameView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameObjects.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects as List<IDrawable>)

            if (gameViewModel.isGameLost()) {
                Navigation.findNavController(view).navigate(R.id.navigateToGameOverFragment)
            }
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

