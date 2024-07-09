package com.example.mygame.logic

import android.util.Log
import kotlin.math.abs

class Score {
    private var value: Double = 0.0

    fun increase(amount: Float) {
        value += abs(amount)
        Log.i("", "Score: $value")
    }
}