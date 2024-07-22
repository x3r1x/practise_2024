package com.example.mygame.UI

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.material3.MediumTopAppBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.Navigation
import com.example.mygame.R
import com.example.mygame.presentation.GameViewModel

class SingleplayerGameFragment : Fragment() {
    private val gameViewModel: GameViewModel by viewModels()

    private var isPaused = false

    private lateinit var gameMusic : ExoPlayer
    private lateinit var pauseMusic: ExoPlayer

    private lateinit var gameSounds: GameSoundsPlayer

    private lateinit var pauseButton: ImageButton
    private lateinit var pauseGroup: ConstraintLayout
    private lateinit var exitToMenuButton: Button
    private lateinit var scoreView: TextView
    private lateinit var gameView: GameView

    private fun initMusics() {
        gameMusic = ExoPlayer.Builder(requireContext()).build()
        pauseMusic = ExoPlayer.Builder(requireContext()).build()

        gameMusic.setMediaItem(MediaItem.fromUri(GAME_MUSIC_URI))
        pauseMusic.setMediaItem(MediaItem.fromUri(PAUSE_MUSIC_URI))

        gameMusic.volume = GAME_MUSIC_VOLUME
        pauseMusic.volume = PAUSE_MUSIC_VOLUME

        gameMusic.repeatMode = Player.REPEAT_MODE_ONE
        pauseMusic.repeatMode = Player.REPEAT_MODE_ONE

        gameMusic.prepare()
        pauseMusic.prepare()
    }

    private fun initViews(view: View) {
        pauseButton = view.findViewById(R.id.pauseButton)
        pauseGroup = view.findViewById(R.id.pauseGroup)
        exitToMenuButton = view.findViewById(R.id.multiplayerButton)
        gameView = view.findViewById(R.id.gameView)
        scoreView = view.findViewById(R.id.scoreView)
    }

    private fun pauseGame() {
        gameViewModel.gameplay.stopGameLoop()

        pauseGroup.visibility = VISIBLE

        gameMusic.pause()
        pauseMusic.play()

        val resumeButton = pauseGroup.findViewById<Button>(R.id.resumeButton)
        resumeButton.setOnClickListener {
            isPaused = false

            pauseMusic.pause()
            gameMusic.play()

            pauseGroup.visibility = INVISIBLE
            gameViewModel.gameplay.startGameLoop()
        }

        exitToMenuButton.setOnClickListener {
            Navigation.findNavController(pauseGroup).navigate(R.id.navigateFromSinglePlayerFragmentToStartFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getScreenSize() : Pair<Float, Float> {
        val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds

        return Pair(bounds.width().toFloat(), bounds.height().toFloat())
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val (screenWidth, screenHeight) = getScreenSize()

        val view = inflater.inflate(R.layout.fragment_game, container, false)
        initViews(view)

        initMusics()
        gameMusic.play()

        gameSounds = GameSoundsPlayer(requireContext())

        gameViewModel.initialize(screenWidth, screenHeight, GameViewModel.Type.SINGLEPLAYER, gameSounds)
        gameViewModel.gameplay.scoreObservable.observe(viewLifecycleOwner) { newScore ->
            scoreView.text = newScore.toString()
        }

        pauseButton.setOnClickListener {
            pauseGame()
        }

        gameView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN && !isPaused) {
                gameSounds.playShootSound()
                gameViewModel.onClick(event.x)
            }
            true
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.gameplay.gameState.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects.objects)

            if (gameViewModel.isGameLost()) {
                val bundle = Bundle()

                bundle.putInt(GameOverFragment.SCORE_ARG, gameViewModel.getScore())
                Navigation.findNavController(view).navigate(R.id.navigateFromSinglePlayerFragmentToGameOverFragment, bundle)
            }
        }

        gameViewModel.gameplay.startGameLoop()
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.gameplay.onResume()

        if (isPaused) {
            pauseMusic.play()
        }
    }

    override fun onPause() {
        super.onPause()
        gameViewModel.gameplay.onPause()
        pauseGame()
        isPaused = true

        pauseMusic.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        gameViewModel.gameplay.stopGameLoop()
    }

    companion object {
        private val GAME_MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.game_music
        private val PAUSE_MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.enter_nickname

        private const val GAME_MUSIC_VOLUME = 0.4f
        private const val PAUSE_MUSIC_VOLUME = 1f
    }
}