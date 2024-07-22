package com.example.mygame.UI

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mygame.R
import com.example.mygame.presentation.GameViewModel

class MultiplayerGameFragment : Fragment() {
    private lateinit var gameView: GameView
    private lateinit var scoreView: TextView
    private lateinit var audioPlayer: GameSoundsPlayer
    private val gameViewModel: GameViewModel by viewModels()

    private fun initViews(view: View) {
        gameView = view.findViewById(R.id.gameView)
        scoreView = view.findViewById(R.id.scoreView)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getScreenSize(): Pair<Float, Float> {
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

        val view = inflater.inflate(R.layout.fragment_multiplayer_game, container, false)
        initViews(view)

        audioPlayer = GameSoundsPlayer(requireContext())

        gameViewModel.initialize(screenWidth, screenHeight, GameViewModel.Type.MULTIPLAYER, audioPlayer)
//        gameViewModel.gameplay.scoreObservable.observe(viewLifecycleOwner) { newScore ->
//            scoreView.text = newScore.toString()
//        }

//        gameView.setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                gameViewModel.onClick(event.x)
//            }
//            true
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.gameplay.gameState.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects.objects)
        }

        //gameViewModel.multiplayerGameplay
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.gameplay.onResume()
    }

    override fun onPause() {
        super.onPause()
        gameViewModel.gameplay.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameViewModel.gameplay.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        gameViewModel.gameplay.onDestroy()
    }
}
