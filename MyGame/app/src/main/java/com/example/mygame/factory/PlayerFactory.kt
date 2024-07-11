package com.example.mygame.factory

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.hardware.biometrics.BiometricManager
import com.example.mygame.R
import com.example.mygame.`object`.Player

class PlayerFactory(resources: Resources) {
    // Инициализация при изменении темы
    val idleImage = BitmapFactory.decodeResource(resources, R.drawable.player)
    val jumpImage = BitmapFactory.decodeResource(resources, R.drawable.jump)
    val deadImage = BitmapFactory.decodeResource(resources, R.drawable.dead_doodler)

    fun generatePlayer() : Player {
        return Player(idleImage, jumpImage, deadImage)
    }
}