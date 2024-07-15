package com.example.mygame.UI

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mygame.R

class EnterNicknameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enter_nickname, container, false)

        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromEnterNicknameFragmentToStartFragment)
        }

        val nicknameView = view.findViewById<TextView>(R.id.nickname)
        val connectButton = view.findViewById<Button>(R.id.connectButton)

        nicknameView.addTextChangedListener {
            if (nicknameView.text.isEmpty()) {
                connectButton.isEnabled = false
                connectButton.setBackgroundTintList(ColorStateList.valueOf(COLOR_GREY))
            } else {
                connectButton.isEnabled = true
                connectButton.setBackgroundTintList(ColorStateList.valueOf(COLOR_GREEN))
            }
        }

        connectButton.setOnClickListener {
            if (connectButton.isEnabled) {
                Navigation.findNavController(view).navigate(R.id.navigateFromEnterNicknameFragmentToLobbyFragment)
            }
        }

        return view
    }

    companion object {
        private val COLOR_GREY = Color.rgb(217, 217, 217)
        private val COLOR_GREEN = Color.rgb(8, 174, 35)
    }
}