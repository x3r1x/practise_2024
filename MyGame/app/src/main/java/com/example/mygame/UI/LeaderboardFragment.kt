package com.example.mygame.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.example.mygame.R
import com.example.mygame.databinding.FragmentLeaderboardBinding
import com.example.mygame.presentation.LeaderboardViewModel
import com.example.mygame.system.viewBindings

class LeaderboardFragment : Fragment(R.layout.fragment_leaderboard) {

    private val viewModel by viewModels<LeaderboardViewModel>()
    private val binding by viewBindings(FragmentLeaderboardBinding::bind)
    private lateinit var audioPlayer: ExoPlayer

    private fun playMusic() {
        audioPlayer = ExoPlayer.Builder(requireContext()).build()
        audioPlayer.setMediaItem(MediaItem.fromUri(MUSIC_URI))

        audioPlayer.volume = VOLUME
        audioPlayer.repeatMode = Player.REPEAT_MODE_ONE

        audioPlayer.prepare()
        audioPlayer.play()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playMusic()

        binding.okButton.setOnClickListener {
            findNavController().popBackStack()
        }

        if (savedInstanceState == null) {
            viewModel.onViewCreated()
        }

        viewModel.state.observe(viewLifecycleOwner, ::render)
    }

    override fun onPause() {
        super.onPause()
        audioPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        audioPlayer.play()
    }

    private fun render(state: LeaderboardViewModel.State) {
        if (state.isFailed) {
            findNavController().popBackStack()
            Toast.makeText(requireContext(), R.string.error_load_scoreboard, Toast.LENGTH_SHORT)
                .show()
            return
        }

        val leaders = state.leaderboard?.leaders

        if (leaders == null) {
            binding.progressBar.isVisible = true
            binding.leaders.isVisible = false
            return
        }

        binding.progressBar.isVisible = false
        binding.leaders.isVisible = true

        val leader1 = leaders.getOrNull(0)
        binding.number1.isVisible = leader1 != null
        binding.number1.text = "#1: ${leader1?.name}   ${leader1?.score}"

        val leader2 = leaders.getOrNull(1)
        binding.number2.isVisible = leader2 != null
        binding.number2.text = "#2: ${leader2?.name}   ${leader2?.score}"

        val leader3 = leaders.getOrNull(2)
        binding.number3.isVisible = leader3 != null
        binding.number3.text = "#3: ${leader3?.name}   ${leader3?.score}"

        val leader4 = leaders.getOrNull(3)
        binding.number4.isVisible = leader4 != null
        binding.number4.text = "#4: ${leader4?.name}   ${leader4?.score}"

        val leader5 = leaders.getOrNull(4)
        binding.number5.isVisible = leader5 != null
        binding.number5.text = "#5: ${leader5?.name}   ${leader5?.score}"

        val leader6 = leaders.getOrNull(5)
        binding.number6.isVisible = leader6 != null
        binding.number6.text = "#6: ${leader6?.name}   ${leader6?.score}"

        val leader7 = leaders.getOrNull(6)
        binding.number7.isVisible = leader7 != null
        binding.number7.text = "#7: ${leader7?.name}   ${leader7?.score}"

    }

    companion object {
        private val MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.leaderboard_music
        private const val VOLUME = 1f
    }
}