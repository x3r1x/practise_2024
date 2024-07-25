package com.example.mygame.UI

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.compose.material3.TopAppBar
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mygame.R
import com.example.mygame.UI.EnterNicknameFragment.Companion
import com.example.mygame.databinding.FragmentGameOverBinding
import com.example.mygame.presentation.GameOverViewModel
import com.example.mygame.system.viewBindings

class GameOverFragment : Fragment() {
    private var score = 0

    private val viewModel by viewModels<GameOverViewModel>()
    private val binding by viewBindings(FragmentGameOverBinding::bind)

    private lateinit var audioPlayer: ExoPlayer
    private lateinit var successSound: ExoPlayer
    private lateinit var declineSound: ExoPlayer

    private fun playSound() {
        audioPlayer = ExoPlayer.Builder(requireContext()).build()
        audioPlayer.setMediaItem(MediaItem.fromUri(MUSIC_URI))

        audioPlayer.volume = VOLUME

        audioPlayer.prepare()
        audioPlayer.play()
    }

    private fun initSounds() {
        successSound = ExoPlayer.Builder(requireContext()).build()
        declineSound = ExoPlayer.Builder(requireContext()).build()

        successSound.setMediaItem(MediaItem.fromUri(SUCCESS_URI))
        declineSound.setMediaItem(MediaItem.fromUri(DECLINE_URI))

        successSound.volume = VOLUME
        declineSound.volume = VOLUME

        successSound.prepare()
        declineSound.prepare()
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
        val nicknameView = view.findViewById<TextView>(R.id.nicknameInput)
        val saveButton = view.findViewById<Button>(R.id.saveResultButton)

        playSound()
        initSounds()

        view.findViewById<Button>(R.id.playAgainButton).setOnClickListener {
            audioPlayer.release()
            Navigation.findNavController(view).navigate(R.id.navigateFromGameOverFragmentToSinglePlayerFragment)
        }

        nicknameView.addTextChangedListener {
            if (nicknameView.text.isEmpty()) {
                saveButton.isEnabled = false
                saveButton.backgroundTintList = ColorStateList.valueOf(COLOR_GREY)
            } else {
                saveButton.isEnabled = true
                saveButton.backgroundTintList = ColorStateList.valueOf(COLOR_GREEN)
            }
        }

        view.findViewById<Button>(R.id.goToMenuButton).setOnClickListener {
            audioPlayer.release()
            declineSound.release()
            successSound.release()
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

        binding.saveResultButton.setOnClickListener {
            viewModel.onViewCreated(
                binding.nicknameInput.text.toString(),
                binding.currentScore.text.toString().toInt()
            )

            viewModel.state.observe(viewLifecycleOwner, ::render)
        }
    }

    private fun render(success: Boolean) {
        if (success) {
            binding.dataEnteringGroup.isVisible = false
            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
            successSound.play()
        } else {
            Toast.makeText(requireContext(), "Sorry, an error occurred :(", Toast.LENGTH_SHORT).show()
            binding.dataEnteringGroup.isVisible = true
            declineSound.play()
            declineSound.seekTo(0)
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

        private val COLOR_GREY = Color.rgb(217, 217, 217)
        private val COLOR_GREEN = Color.rgb(8, 174, 35)

        private val MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.game_over_sound
        private val SUCCESS_URI = "android.resource://com.example.mygame/" + R.raw.success_sound
        private val DECLINE_URI = "android.resource://com.example.mygame/" + R.raw.decline_sound

        private const val VOLUME = 1f
    }
}