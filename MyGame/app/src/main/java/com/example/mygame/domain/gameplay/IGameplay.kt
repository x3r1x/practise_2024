package com.example.mygame.domain.gameplay

import androidx.lifecycle.LiveData
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.IGameObject

enum class Type {
    LOBBY,
    GAME
}

data class GameState(
    val type: Type,
    var objects: List<IDrawable>,
    val lobbyPlayers: List<String>
)

interface IGameplay {
    val gameState: LiveData<GameState>
    fun onViewCreated()

    fun onShot(startX: Float)
    fun onPause()
    fun onResume()
    fun onDestroy()
}