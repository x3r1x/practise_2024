package com.example.mygame.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygame.domain.leaderboard.Leaderboard
import com.example.mygame.multiplayer.Repository
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel() {

    data class State(
        val leaderboard: Leaderboard? = null,
        val isFailed: Boolean = false
    ) {

        data class Leader(
            val name: String,
            val score: Int
        )

    }

    val state = MutableLiveData(State())

    private val gateway = Repository()

    fun onViewCreated() {
        viewModelScope.launch {
            when (val leaderboard = gateway.getLeaderboard()) {
                null -> {
                    state.value = state.value?.copy(isFailed = true)
                }
                else -> {
                    state.value = state.value?.copy(leaderboard = leaderboard)
                }
            }
        }
    }

}
