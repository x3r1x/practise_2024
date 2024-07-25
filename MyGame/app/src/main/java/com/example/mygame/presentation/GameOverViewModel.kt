package com.example.mygame.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygame.multiplayer.Repository
import kotlinx.coroutines.launch

class GameOverViewModel : ViewModel() {
    val state = MutableLiveData<Boolean>()

    private val gateway = Repository()

    fun onViewCreated(user: String, score: Int) {
        viewModelScope.launch {
            state.value = gateway.sendResult(user, score)
            println(state.value)
        }
    }
}