package com.example.mygame.`object`

import kotlin.math.abs

class Score {
    private var viewValue = 0.0

    fun increase(amount: Float) {
        viewValue += abs(amount / 10)
    }

    fun getScore() : Int {
        return viewValue.toInt()
    }
}