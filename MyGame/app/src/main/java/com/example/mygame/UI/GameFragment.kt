package com.example.mygame.UI

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.mygame.R
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.fragment.app.viewModels
import com.example.mygame.presentation.GameViewModel

class GameFragment : Fragment() {
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var pauseButton: ImageButton
    private lateinit var pauseGroup: ConstraintLayout
    private lateinit var exitToMenuButton: Button
    private lateinit var scoreView: TextView
    private lateinit var gameView: GameView

    private fun initViews(view: View) {
        pauseButton = view.findViewById(R.id.pauseButton)
        pauseGroup = view.findViewById(R.id.pauseGroup)
        exitToMenuButton = view.findViewById(R.id.multiplayerButton)
        gameView = view.findViewById(R.id.gameView)
        scoreView = view.findViewById(R.id.scoreView)
    }

    private fun pauseGame() {
//        gameViewModel.gameplay.stopGameLoop()

        pauseGroup.visibility = VISIBLE

        val resumeButton = pauseGroup.findViewById<Button>(R.id.resumeButton)
        resumeButton.setOnClickListener {
            pauseGroup.visibility = INVISIBLE
//            gameViewModel.gameplay.startGameLoop()

        }

        exitToMenuButton.setOnClickListener {
            Navigation.findNavController(pauseGroup).navigate(R.id.navigateFromSinglePlayerFragmentToStartFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getScreenSize() : Pair<Float, Float> {
        val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds

        return Pair(bounds.width().toFloat(), bounds.height().toFloat())
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val (screenWidth, screenHeight) = getScreenSize()

        val view = inflater.inflate(R.layout.fragment_game, container, false)
        initViews(view)

        gameViewModel.initialize(screenWidth, screenHeight)
//        gameViewModel.gameplay.scoreObservable.observe(viewLifecycleOwner) { newScore ->
//            scoreView.text = newScore.toString()
//        }

        pauseButton.setOnClickListener {
            pauseGame()
        }

        gameView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                gameViewModel.onClick(event.x)
            }
            true
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameplay.gameState.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects.objects)

            if (gameViewModel.isGameLost()) {
                val bundle = Bundle()
                bundle.putInt(GameOverFragment.SCORE_ARG, gameViewModel.getScore())
                Navigation.findNavController(view).navigate(R.id.navigateFromSinglePlayerFragmentToGameOverFragment, bundle)
            }
        }

        // Запуск игрового цикла
//        gameViewModel.gameplay.startGameLoop()
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.gameplay.onResume()
    }

    override fun onPause() {
        super.onPause()
        gameViewModel.gameplay.onPause()
        pauseGame()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        gameViewModel.gameplay.stopGameLoop()
    }
}