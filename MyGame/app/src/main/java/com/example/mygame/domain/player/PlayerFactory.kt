package com.example.mygame.domain.player

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R

class PlayerFactory(resources: Resources) {
    // Инициализация при изменении темы
    val idleImage = BitmapFactory.decodeResource(resources, R.drawable.player)
    val jumpImage = BitmapFactory.decodeResource(resources, R.drawable.jump)
    val deadImage = BitmapFactory.decodeResource(resources, R.drawable.dead_doodler)
    val shootImage = BitmapFactory.decodeResource(resources, R.drawable.player_shooting)

    fun generatePlayer() : Player {
        return Player(idleImage, jumpImage, deadImage, shootImage)
    }
}