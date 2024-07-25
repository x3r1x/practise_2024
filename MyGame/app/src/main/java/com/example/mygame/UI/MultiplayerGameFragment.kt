package com.example.mygame.UI

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.mygame.R
import com.example.mygame.domain.gameplay.MultiplayerGameplay
import com.example.mygame.domain.gameplay.Type
import com.example.mygame.presentation.GameViewModel

class MultiplayerGameFragment : Fragment() {
    private lateinit var gameView: GameView
    private lateinit var scoreView: TextView
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var readyButton: Button

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

        val helloGroup = view.findViewById<ConstraintLayout>(R.id.helloGroup)
        val helloTextView = view.findViewById<TextView>(R.id.helloTextView)

        val resultGroup = view.findViewById<ConstraintLayout>(R.id.resultGroup)
        val resultTextView = resultGroup.findViewById<TextView>(R.id.winnerText)
        val goToMenuButton = resultGroup.findViewById<Button>(R.id.returnToMenuButton)

        val waitingGroup = view.findViewById<ConstraintLayout>(R.id.waitingGroup)
        val waitingReturnButton = view.findViewById<Button>(R.id.waitReturnButton)

        gameViewModel.initialize(screenWidth, screenHeight, GameViewModel.Type.MULTIPLAYER, requireContext(), lifecycleScope)

        gameViewModel.gameplay.gameState.observe(viewLifecycleOwner) {
            if (gameViewModel.gameplay.id == 0) {
                waitingGroup.visibility = VISIBLE
                waitingReturnButton.setOnClickListener {
                    Navigation.findNavController(view).navigate(R.id.action_multiplayerGameFragment_to_startFragment)
                }
            } else if (gameViewModel.gameplay.gameState.value!!.type == Type.LOBBY) {
                waitingGroup.visibility = GONE
                helloTextView.text = "Hello, Player ${gameViewModel.gameplay.id}!"
                helloGroup.visibility = VISIBLE
            } else if (gameViewModel.gameplay.gameState.value!!.type == Type.GAME) {
                helloGroup.visibility = GONE
                gameView.visibility = VISIBLE
            } else if (gameViewModel.gameplay.gameState.value!!.type == Type.END) {
                val id = gameViewModel.gameplay.id
                val winnerId = gameViewModel.gameplay.winnerId
                gameView.alpha = 0.2f

                if (id == winnerId) {
                    resultTextView.text = "You won!"
                } else {
                    resultTextView.text = "You lose! Player ${winnerId} won!"
                }

                resultGroup.visibility = VISIBLE

                goToMenuButton.setOnClickListener {
                    Navigation.findNavController(view).navigate(R.id.action_multiplayerGameFragment_to_startFragment)
                }
            }
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.onViewCreated()
        gameViewModel.gameplay.gameState.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects.objects)
        }
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
