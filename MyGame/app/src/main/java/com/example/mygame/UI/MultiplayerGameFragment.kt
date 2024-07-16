package com.example.mygame.UI

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mygame.R
import com.example.mygame.presentation.GameViewModel

class MultiplayerGameFragment : Fragment() {
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var gameView: GameView


    private lateinit var scoreView: TextView

    private fun initViews(view: View) {
        gameView = view.findViewById(R.id.gameView)
        scoreView = view.findViewById<TextView>(R.id.scoreView)
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

        gameViewModel.initialize(screenWidth, screenHeight)

        val view = inflater.inflate(R.layout.fragment_multiplayer_game, container, false)

        initViews(view)

        gameViewModel.scoreObservable.observe(viewLifecycleOwner) { newScore ->
            scoreView.text = newScore.toString()
        }

        gameView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                gameViewModel.onClick(event.x, event.y)
            }
            true
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameObjects.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects as List<IDrawable>)

            if (gameViewModel.isGameLost()) {
                val bundle = Bundle()
                bundle.putInt(GameOverFragment.SCORE_ARG, gameViewModel.getScore())
                Navigation.findNavController(view).navigate(R.id.navigateFromSinglePlayerFragmentToGameOverFragment, bundle)
            }
        }

        // Запуск игрового цикла
        gameViewModel.startGameLoop()
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.registerSensorHandler()
    }

    override fun onPause() {
        super.onPause()
        //gameViewModel.unregisterSensorHandler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //gameViewModel.stopGameLoop()
    }
}

