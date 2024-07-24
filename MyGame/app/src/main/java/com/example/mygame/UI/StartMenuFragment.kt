package com.example.mygame.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.Navigation
import com.example.mygame.R

class StartMenuFragment : Fragment() {
    private lateinit var audioPlayer: ExoPlayer

    private fun playMusic() {
        audioPlayer = ExoPlayer.Builder(requireContext()).build()
        audioPlayer.setMediaItem(MediaItem.fromUri(MUSIC_URI))

        audioPlayer.volume = VOLUME
        audioPlayer.repeatMode = Player.REPEAT_MODE_ONE

        audioPlayer.prepare()
        audioPlayer.play()
    }

    private fun addOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_menu, container, false)

        addOnBackPressed()

        playMusic()

        view.findViewById<Button>(R.id.singlePlayerButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromStartFragmentToSinglePlayerFragment)
        }

        view.findViewById<Button>(R.id.multiplayerButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromStartMenuFragmentToMultiplayerFragment)
        }

        view.findViewById<Button>(R.id.leaderboardButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromStartFragmentToLeaderboardFragment)
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
        private val MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.menu_music
        private const val VOLUME = 1f
    }
}