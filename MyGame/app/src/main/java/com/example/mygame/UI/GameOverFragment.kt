package com.example.mygame.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.Navigation
import com.example.mygame.R

class GameOverFragment : Fragment() {
    private var score = 0

    private lateinit var audioPlayer: ExoPlayer

    private fun playSound() {
        audioPlayer = ExoPlayer.Builder(requireContext()).build()
        audioPlayer.setMediaItem(MediaItem.fromUri(MUSIC_URI))

        audioPlayer.volume = VOLUME

        audioPlayer.prepare()
        audioPlayer.play()
    }

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

        playSound()

        view.findViewById<Button>(R.id.playAgainButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromGameOverFragmentToSinglePlayerFragment)
        }

        view.findViewById<Button>(R.id.goToMenuButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromGameOverFragmentToMenuFragment)
        }

        view.findViewById<TextView>(R.id.currentScore).text = score.toString()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromGameOverFragmentToMenuFragment)
        }
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
        const val SCORE_ARG = "score"

        private val MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.game_over_sound
        private const val VOLUME = 1f
    }
}