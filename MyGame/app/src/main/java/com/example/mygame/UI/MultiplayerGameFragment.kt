package com.example.mygame.UI

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.mygame.R
import com.example.mygame.domain.IGameObject
import com.example.mygame.multiplayer.WebSocketManager
import java.net.URI

class MultiplayerGameFragment : Fragment() {
    private lateinit var gameView: GameView
    private lateinit var scoreView: TextView
    private lateinit var webSocketManager: WebSocketManager

    private fun initViews(view: View) {
        gameView = view.findViewById(R.id.gameView)
        scoreView = view.findViewById(R.id.scoreView)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getScreenSize(): Pair<Float, Float> {
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

        val view = inflater.inflate(R.layout.fragment_multiplayer_game, container, false)
        initViews(view)

        webSocketManager = activity?.let { WebSocketManager(it, URI("ws://10.250.105.20:8080")) }!!
        webSocketManager.connect()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webSocketManager.storage.getObjects()?.let { gameView.drawGame(it as List<IDrawable>) }
    }

    override fun onResume() {
        super.onResume()
        // Register sensor handler if needed
    }

    override fun onPause() {
        super.onPause()
        // Unregister sensor handler if needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webSocketManager.close()
    }
}
