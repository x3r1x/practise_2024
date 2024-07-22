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
import com.example.mygame.R
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.material3.MediumTopAppBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.mygame.presentation.GameViewModel

class GameFragment : Fragment() {
    private val gameViewModel: GameViewModel by viewModels()

    private var isPaused = false

    private lateinit var gameMusic : ExoPlayer
    private lateinit var pauseMusic: ExoPlayer

    private lateinit var gameSounds: GameSoundsPlayer

    private lateinit var gameView: GameView

    private lateinit var pauseGroup: ConstraintLayout
    private lateinit var exitToMenuButton: Button

    private fun pause() {
        gameViewModel.stopGameLoop()
        pauseGroup.visibility = VISIBLE

        gameMusic.pause()
        pauseMusic.play()

        val resumeButton = pauseGroup.findViewById<Button>(R.id.resumeButton)
        resumeButton.setOnClickListener {
            isPaused = false

            pauseMusic.pause()
            gameMusic.play()

            pauseGroup.visibility = INVISIBLE
            gameViewModel.startGameLoop()
        }

        exitToMenuButton.setOnClickListener {
            Navigation.findNavController(pauseGroup).navigate(R.id.navigateFromSinglePlayerFragmentToStartFragment)

            gameMusic.stop()
            pauseMusic.stop()
            gameSounds.player.release()
        }
    }

    private fun initializeAudioPlayers() {
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

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Получаем размеры экрана
        val windowMetrics: WindowMetrics = requireActivity().windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        val screenWidth = bounds.width().toFloat()
        val screenHeight = bounds.height().toFloat()

        initializeAudioPlayers()
        gameMusic.play()

        gameSounds = GameSoundsPlayer(requireContext())
        gameViewModel.initialize(screenWidth, screenHeight, gameSounds)

        val view = inflater.inflate(R.layout.fragment_game, container, false)

        gameView = view.findViewById(R.id.gameView)

        val pauseButton = view.findViewById<ImageButton>(R.id.pauseButton)
        val scoreView = view.findViewById<TextView>(R.id.scoreView)

        pauseGroup = view.findViewById<ConstraintLayout>(R.id.pauseGroup)
        exitToMenuButton = view.findViewById<Button>(R.id.multiplayerButton)

        gameViewModel.scoreObservable.observe(viewLifecycleOwner) { newScore ->
            scoreView.text = newScore.toString()
        }

        pauseButton.setOnClickListener {
            isPaused = true
            pause()
        }

        gameView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN && !isPaused) {
                gameSounds.playShootSound()
                gameViewModel.onClick(event.x, event.y)
            }
            true
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Наблюдаем за изменениями в объектах игры
        gameViewModel.gameObjects.observe(viewLifecycleOwner) { gameObjects ->
            gameView.drawGame(gameObjects as List<IDrawable>)

            if (gameViewModel.isGameLost()) {
                val bundle = Bundle()

                bundle.putInt(GameOverFragment.SCORE_ARG, gameViewModel.getScore())
                Navigation.findNavController(view).navigate(R.id.navigateFromSinglePlayerFragmentToGameOverFragment, bundle)
            }
        }

        // Запускаем игровой цикл через ViewModel
        gameViewModel.startGameLoop()
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.registerSensorHandler()

        if (isPaused) {
            pauseMusic.play()
        }
    }

    override fun onPause() {
        super.onPause()
        gameViewModel.unregisterSensorHandler()
        isPaused = true
        pause()

        pauseMusic.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        gameViewModel.stopGameLoop()
    }

    companion object {
        private val GAME_MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.game_music
        private val PAUSE_MUSIC_URI = "android.resource://com.example.mygame/" + R.raw.enter_nickname

        private const val GAME_MUSIC_VOLUME = 0.4f
        private const val PAUSE_MUSIC_VOLUME = 1f
    }
}

