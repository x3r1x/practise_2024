package com.example.mygame.domain.player

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R

class PlayerFactory(resources: Resources) {
    private val idleImage: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.player)
    private val jumpImage = BitmapFactory.decodeResource(resources, R.drawable.jump)
    private val deadImage = BitmapFactory.decodeResource(resources, R.drawable.dead_doodler)
    private val shootImage = BitmapFactory.decodeResource(resources, R.drawable.player_shooting)

    fun generatePlayer() : Player {
        return Player(idleImage, jumpImage, deadImage, shootImage)
    }
}