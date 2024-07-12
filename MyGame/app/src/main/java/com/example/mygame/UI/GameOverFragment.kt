package com.example.mygame.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mygame.R

class GameOverFragment : Fragment() {
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getInt(SCORE_ARG, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        view.findViewById<Button>(R.id.playAgainButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromGameOverFragmentToSinglePlayerFragment)
        }

        view.findViewById<Button>(R.id.goToMenuButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromGameOverFragmentToMenuFragment)
        }

        view.findViewById<TextView>(R.id.currentScore).text = score.toString()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Navigation.findNavController(view).navigate(R.id.navigateFromGameOverFragmentToMenuFragment)
        }
    }

    companion object {
        const val SCORE_ARG = "score"
    }
}