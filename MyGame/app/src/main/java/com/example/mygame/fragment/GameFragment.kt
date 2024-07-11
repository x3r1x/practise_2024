package com.example.mygame.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import com.example.mygame.R
import android.view.ViewGroup
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.fragment.app.viewModels
import com.example.mygame.GameViewModel
import com.example.mygame.view.GameView
import com.example.mygame.`interface`.IDrawable

class GameFragment : Fragment() {
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var gameView: GameView

    @SuppressLint("ClickableViewAccessibility")
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

        gameView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                gameViewModel.onClick(event.x, event.y)
            }
            true
        }

        return gameView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameObjects.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects as List<IDrawable>)



            if (gameViewModel.isGameLost()) {
                val bundle = Bundle()
                bundle.putInt(GameOverFragment.SCORE_ARG, gameViewModel.returnScore())
                Navigation.findNavController(view).navigate(R.id.navigateToGameOverFragment, bundle)
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

