package com.example.mygame.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mygame.GameViewModel
import com.example.mygame.R
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.view.GameView

class GameFragment : Fragment() {
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var gameView: GameView

    private fun setupPauseButtonClickListener(
        pauseButton: ImageButton,
        pauseGroup: ConstraintLayout,
        exitToMenuButton: Button
    ) {
        pauseButton.setOnClickListener {
            gameViewModel.stopGameLoop()
            pauseGroup.visibility = VISIBLE

            val resumeButton = pauseGroup.findViewById<Button>(R.id.resumeButton)
            resumeButton.setOnClickListener {
                pauseGroup.visibility = INVISIBLE
                gameViewModel.startGameLoop()
            }

            exitToMenuButton.setOnClickListener {
                Navigation.findNavController(pauseGroup).navigate(R.id.navigateFromSinglePlayerFragmentToStartFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Получаем размеры экрана
        val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val screenWidth = bounds.width().toFloat()
        val screenHeight = bounds.height().toFloat()

        gameViewModel.initialize(screenWidth, screenHeight)

        val view = inflater.inflate(R.layout.fragment_game, container, false)

        gameView = view.findViewById(R.id.gameView)

        val pauseButton = view.findViewById<ImageButton>(R.id.pauseButton)
        val pauseGroup = view.findViewById<ConstraintLayout>(R.id.pauseGroup)
        val exitToMenuButton = view.findViewById<Button>(R.id.exitToMenuButton)
        val scoreView = view.findViewById<TextView>(R.id.scoreView)

        gameViewModel.scoreObservable.observe(viewLifecycleOwner) { newScore ->
            scoreView.text = newScore.toString()
        }

        setupPauseButtonClickListener(pauseButton, pauseGroup, exitToMenuButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameObjects.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects as List<IDrawable>)

            if (gameViewModel.isGameLost()) {
                val bundle = Bundle()
                bundle.putInt(GameOverFragment.SCORE_ARG, gameViewModel.returnScore())
                Navigation.findNavController(view).navigate(R.id.navigateFromSinglePlayerFragmentToGameOverFragment, bundle)
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

