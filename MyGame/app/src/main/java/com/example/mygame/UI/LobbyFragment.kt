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
import androidx.compose.material3.MediumTopAppBar
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.Navigation
import com.example.mygame.R

class LobbyFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_lobby, container, false)

        playMusic()

        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromLobbyFragmentToStartFragment)
        }

        val readyButton = view.findViewById<Button>(R.id.readyButton)
        val waitingTextView = view.findViewById<TextView>(R.id.waitingTextView)

        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromLobbyFragmentToStartFragment)
        }

        readyButton.setOnClickListener {
            readyButton.visibility = INVISIBLE
            waitingTextView.visibility = VISIBLE
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
        private val MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.lobby_music
        private const val VOLUME = 1f
    }
}