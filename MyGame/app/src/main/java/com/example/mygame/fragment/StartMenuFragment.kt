package com.example.mygame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mygame.R

class StartMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_menu, container, false)

        view.findViewById<Button>(R.id.singleplayerButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToSinglePlayerFragment)
        }

        return view
    }
}