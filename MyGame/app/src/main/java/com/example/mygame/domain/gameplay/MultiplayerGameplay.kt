package com.example.mygame.domain.gameplay

import android.content.res.Resources
import androidx.lifecycle.LiveData
import com.example.mygame.domain.Screen
import com.example.mygame.multiplayer.JSONToKotlin

class MultiplayerGameplay(resources: Resources, screen: Screen) : IGameplay {
    override val gameState: LiveData<GameState> = TODO()

    override fun onViewCreated() {
        TODO("Not yet implemented")
    }

    override fun onShot(startX: Float, startY: Float) {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }
}