package com.example.mygame.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.mygame.R

class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        view.findViewById<Button>(R.id.playAgainButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToSinglePlayerFragment)
        }

        view.findViewById<Button>(R.id.goToMenuButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToMenuFragment)
        }

        return view
    }
}