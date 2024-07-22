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
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.Navigation
import com.example.mygame.R

class EnterNicknameFragment : Fragment() {
    private lateinit var audioPlayer: ExoPlayer

    private fun playMusic() {
        audioPlayer = ExoPlayer.Builder(requireContext()).build()
        audioPlayer.setMediaItem(MediaItem.fromUri(MUSIC_URI))

        audioPlayer.volume = VOLUME
        audioPlayer.repeatMode = Player.REPEAT_MODE_ONE

        audioPlayer.prepare()
        audioPlayer.play()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_enter_nickname, container, false)

        playMusic()

        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromEnterNicknameFragmentToStartFragment)
        }

        val nicknameView = view.findViewById<TextView>(R.id.nickname)
        val connectButton = view.findViewById<Button>(R.id.connectButton)

        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromEnterNicknameFragmentToStartFragment)
        }

        nicknameView.addTextChangedListener {
            if (nicknameView.text.isEmpty()) {
                connectButton.isEnabled = false
                connectButton.backgroundTintList = ColorStateList.valueOf(COLOR_GREY)
            } else {
                connectButton.isEnabled = true
                connectButton.backgroundTintList = ColorStateList.valueOf(COLOR_GREEN)
            }
        }

        connectButton.setOnClickListener {
            if (connectButton.isEnabled) {
                audioPlayer.release()
                Navigation.findNavController(view).navigate(R.id.navigateFromEnterNicknameFragmentToLobbyFragment)
            }
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        audioPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        audioPlayer.play()
    }

    companion object {
        private val COLOR_GREY = Color.rgb(217, 217, 217)
        private val COLOR_GREEN = Color.rgb(8, 174, 35)

        private val MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.enter_nickname
        private const val VOLUME = 1f
    }
}