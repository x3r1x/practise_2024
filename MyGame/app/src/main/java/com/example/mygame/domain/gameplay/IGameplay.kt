package com.example.mygame.domain.gameplay

import androidx.lifecycle.LiveData
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.Score

enum class Type(var value: String) {
    LOBBY("l"),
    GAME("r"),
    END("e")
}

data class GameState(
    val type: Type,
    var objects: List<IDrawable>,
    val lobbyPlayers: List<String>
)

interface IGameplay {
    val gameState: LiveData<GameState>
    val score: Score
    val scoreObservable: LiveData<Int>

    var id: Int
    var winnerId: Int

    fun onViewCreated() {}

    fun startGameLoop() {}
    fun stopGameLoop() {}

    fun onShot(startX: Float)
    fun onPause() {}
    fun onResume() {}
    fun onDestroy()
    fun onSensorDataChanged(deltaX: Float)

    fun sendReadyMessage()
}
