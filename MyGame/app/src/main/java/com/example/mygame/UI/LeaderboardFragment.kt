package com.example.mygame.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.material3.NavigationBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.Navigation
import com.example.mygame.R
import com.example.mygame.UI.StartMenuFragment.Companion

class LeaderboardFragment : Fragment() {
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        playMusic()

        view.findViewById<Button>(R.id.okButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateFromLeaderboardFragmentToStartFragment)
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
        private val MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.leaderboard_music
        private const val VOLUME = 1f
    }
}