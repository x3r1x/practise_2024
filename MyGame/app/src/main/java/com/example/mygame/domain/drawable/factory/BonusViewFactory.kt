package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView

class BonusViewFactory(private val resources: Resources) {
    private val shieldBitmap = BitmapFactory.decodeResource(resources, R.drawable.shield, BITMAP_OPTIONS)
    private val shieldOnPlayerBitmap = BitmapFactory.decodeResource(resources, R.drawable.shield_on_player, BITMAP_OPTIONS)
    private val springBitmaps = listOf(
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_1, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_2, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_3, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.spring_state_4, BITMAP_OPTIONS)
    )
    private val jetpackBitmap = BitmapFactory.decodeResource(resources, R.drawable.jetpack, BITMAP_OPTIONS)
    private val jetpackOnPlayerBitmap = listOf(
        BitmapFactory.decodeResource(resources, R.drawable.player_jetpack_right, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.player_jetpack_left, BITMAP_OPTIONS)
    )

    fun getBonusView(
        x: Float,
        y: Float,
        type: String,
        another: Int
    ) : ObjectView {
        val bitmap = getBitmap(type, another)

        return ObjectView(x, y, bitmap, Matrix())
    }

    private fun getBitmap(type: String, animation: Int) : Bitmap {
        if (type == SHIELD) {
            when (animation) {
                0 -> return shieldBitmap
                1 -> return shieldOnPlayerBitmap
                else -> throw IllegalArgumentException("Invalid shield animation value: $animation")
            }
        } else if (type == JETPACK) {
            return  jetpackBitmap
        } else if (type == JETPACK_ON_PLAYER) {
            when (animation) {
                0 -> return jetpackOnPlayerBitmap[0]
                1 -> return jetpackOnPlayerBitmap[1]
                else -> throw IllegalArgumentException("Invalid jetpack animation value: $animation")
            }
        } else {
            when (animation) {
                0 -> return springBitmaps[0]
                1 -> return springBitmaps[1]
                2 -> return springBitmaps[2]
                3 -> return springBitmaps[3]
                else -> throw IllegalArgumentException("Invalid spring animation value: $animation")
            }
        }
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        const val SHIELD = "shield"
        const val SPRING = "spring"
        const val JETPACK = "jetpack"
        const val JETPACK_ON_PLAYER = "jetpackOnPlayer"
    }
}