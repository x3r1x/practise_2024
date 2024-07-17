package com.example.mygame.multiplayer.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView
import com.example.mygame.multiplayer.PlayerJSON

class PlayerFactory(private val resources: Resources) {
    private val idlePlayerBitmap = BitmapFactory.decodeResource(resources, R.drawable.player)
    private val jumpPlayerBitmap = BitmapFactory.decodeResource(resources, R.drawable.jump)
    private val deadPlayerBitmap = BitmapFactory.decodeResource(resources, R.drawable.dead_doodler)
    private val shootPlayerBitmap = BitmapFactory.decodeResource(resources, R.drawable.player_shooting)

    fun getPlayerView(playerJSON: PlayerJSON) : ObjectView {
        val x = playerJSON.x
        val y = playerJSON.y
        val state = playerJSON.stt
        val directionX = playerJSON.drx
        val directionY = playerJSON.dry

        val bitmap = getBitmap(state, directionY)
        val rect = getRect(x, y, state)
        val matrix = getMatrix(directionX, rect, bitmap)

        return ObjectView(x, y, bitmap, matrix)
    }

    private fun getBitmap(state: String, directionY: Int): Bitmap {
        return if (state == DEAD) {
            deadPlayerBitmap
        } else if (state == SHOOTING) {
            shootPlayerBitmap
        } else if (directionY == DIRECTION_Y_UP) {
            jumpPlayerBitmap
        } else {
            idlePlayerBitmap
        }
    }

    private fun getMatrix(directionX: Int, destRect: RectF, bitmap: Bitmap) : Matrix {
        val scaleX = destRect.width() / bitmap.width
        val scaleY = destRect.height() / bitmap.height

        val matrix = Matrix()

        if (directionX == DIRECTION_X_LEFT) {
            matrix.preScale(-scaleX, scaleY)
            matrix.postTranslate(destRect.right, destRect.top)
        } else {
            matrix.postScale(scaleX, scaleY)
            matrix.postTranslate(destRect.left, destRect.top)
        }

        return matrix
    }

    private fun getRect(x: Float, y: Float, state: String) : RectF {
        return if (state == DEAD) {
            RectF(x - DEAD_WIDTH / 2, y - DEAD_HEIGHT / 2, x + DEAD_WIDTH / 2, y + DEAD_HEIGHT / 2)
        } else if (state == SHOOTING) {
            RectF(x - SHOOTING_WIDTH / 2, y - SHOOTING_HEIGHT / 2, x + SHOOTING_WIDTH / 2, y + SHOOTING_HEIGHT / 2)
        } else {
            RectF(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS)
        }
    }

    companion object {
        const val DEAD = "dead"
        const val SHOOTING = "shooting"

        const val DIRECTION_X_LEFT = 0
        const val DIRECTION_X_RIGHT = 1

        const val DIRECTION_Y_UP = 0
        const val DIRECTION_Y_DOWN = 1

        const val SHOOTING_WIDTH = 105f
        const val SHOOTING_HEIGHT = 200f

        private const val RADIUS = 75f

        private const val DEAD_WIDTH = 120f
        private const val DEAD_HEIGHT = 190f
    }
}