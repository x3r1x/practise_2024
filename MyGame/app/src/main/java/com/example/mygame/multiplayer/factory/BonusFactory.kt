package com.example.mygame.multiplayer.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView
import com.example.mygame.multiplayer.BonusJSON
import com.example.mygame.multiplayer.EnemyJSON

class BonusFactory(private val resources: Resources) {
    private val shieldImage = R.drawable.shield
    private val shieldOnPlayerImage = R.drawable.shield_on_player
    private val springImages = listOf(
        R.drawable.spring_state_1,
        R.drawable.spring_state_2,
        R.drawable.spring_state_3,
        R.drawable.spring_state_4
    )

    fun getBonusView(bonusJSON: BonusJSON) : ObjectView {
        val x = bonusJSON.x
        val y = bonusJSON.y
        val type = bonusJSON.typ
        val animation = bonusJSON.anm

        val bitmap = getBitmap(type, animation)

        return ObjectView(x, y, bitmap, Matrix())
    }

    private fun getBitmap(type: String, animation: Int) : Bitmap {
        if (type == SHIELD) {
            when (animation) {
                0 -> return BitmapFactory.decodeResource(resources, shieldImage)
                1 -> return BitmapFactory.decodeResource(resources, shieldOnPlayerImage)
                else -> throw IllegalArgumentException("Invalid animation value: $animation")
            }
        } else {
            when (animation) {
                0 -> return BitmapFactory.decodeResource(resources, springImages[0])
                1 -> return BitmapFactory.decodeResource(resources, springImages[1])
                2 -> return BitmapFactory.decodeResource(resources, springImages[2])
                3 -> return BitmapFactory.decodeResource(resources, springImages[3])
                else -> throw IllegalArgumentException("Invalid animation value: $animation")
            }
        }
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        private const val SHIELD = "shield"
        private const val SPRING = "spring"
    }
}