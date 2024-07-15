package com.example.mygame.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mygame.R

class LobbyFragment : Fragment() {
    /*private val textViewsOfPlayers = arrayOf(
            R.id.firstPlayer,
            R.id.secondPlayer,
            R.id.thirdPlayer,
            R.id.fourthPlayer,
            R.id.fifthPlayer,
            R.id.sixthPlayer
    )*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lobby, container, false)

        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromLobbyFragmentToStartFragment)
        }

        val readyButton = view.findViewById<Button>(R.id.readyButton)
        val waitingTextView = view.findViewById<TextView>(R.id.waitingTextView)

        readyButton.setOnClickListener {
            readyButton.visibility = INVISIBLE
            waitingTextView.visibility = VISIBLE
        }

        return view
    }
}