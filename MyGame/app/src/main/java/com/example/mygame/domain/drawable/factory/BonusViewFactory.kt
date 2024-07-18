package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.bonuses.Jetpack.Companion.HEIGHT
import com.example.mygame.domain.bonuses.Jetpack.Companion.OFFSET_ON_PLAYER_LEFT
import com.example.mygame.domain.bonuses.Jetpack.Companion.WIDTH
import com.example.mygame.domain.drawable.ObjectView

class BonusViewFactory(resources: Resources) {
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
        BitmapFactory.decodeResource(resources, R.drawable.player_jetpack_left, BITMAP_OPTIONS),
        BitmapFactory.decodeResource(resources, R.drawable.player_jetpack_right, BITMAP_OPTIONS)
    )

    fun getBonusView(
        x: Float,
        y: Float,
        type: String,
        another: Int
    ) : ObjectView {
        var newX = x
        if (type == JETPACK_ON_PLAYER_LEFT) {
            newX += OFFSET_ON_PLAYER_LEFT
        } else if (type == JETPACK_ON_PLAYER_RIGHT) {
            newX -= OFFSET_ON_PLAYER_LEFT
        }

        val bitmap = getBitmap(type, another)
        val rect = getRect(newX, y, bitmap)
        val matrix = getMatrix(rect)

        return ObjectView(newX, y, bitmap, matrix)
    }

    private fun getBitmap(type: String, animation: Int) : Bitmap {
        if (type == SHIELD) {
            return shieldBitmap
        } else if (type == SHIELD_ON_PLAYER) {
            return shieldOnPlayerBitmap
        } else if (type == JETPACK) {
            return jetpackBitmap
        } else if (type == JETPACK_ON_PLAYER_LEFT) {
            return jetpackOnPlayerBitmap[0]
        } else if (type == JETPACK_ON_PLAYER_RIGHT) {
            return jetpackOnPlayerBitmap[1]
        }
        else {
            when (animation) {
                0 -> return springBitmaps[0]
                1 -> return springBitmaps[1]
                2 -> return springBitmaps[2]
                3 -> return springBitmaps[3]
                else -> throw IllegalArgumentException("Invalid spring animation value: $animation")
            }
        }
    }

    private fun getMatrix(destRect: RectF) : Matrix {
        val matrix = Matrix()
        matrix.postTranslate(destRect.left, destRect.top)

        return matrix
    }

    private fun getRect(x: Float, y: Float, bitmap: Bitmap) : RectF {
        return RectF(x - bitmap.width / 2, y - bitmap.height / 2, x + bitmap.width / 2, y + bitmap.height / 2)
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        const val SHIELD = "shield"
        const val SHIELD_ON_PLAYER = "shieldOnPlayer"
        const val SPRING = "spring"
        const val JETPACK = "jetpack"
        const val JETPACK_ON_PLAYER_LEFT = "jetpackOnPlayerLeft"
        const val JETPACK_ON_PLAYER_RIGHT = "jetpackOnPlayerRight"
    }
}