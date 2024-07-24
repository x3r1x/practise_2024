package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectType.Companion.TRUE
import com.example.mygame.domain.drawable.view.ObjectView

class PlayerViewFactory(resources: Resources) {
    private val idlePlayerBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.player, BITMAP_OPTIONS)
    private val jumpPlayerBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.jump, BITMAP_OPTIONS)
    private val deadPlayerBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.dead_doodler, BITMAP_OPTIONS)
    private val shootPlayerBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.player_shooting, BITMAP_OPTIONS)

    private val selectedBonusViewFactory = SelectedBonusViewFactory(resources)

    fun getPlayerView(
        x: Float,
        y: Float,
        directionX: Int,
        directionY: Int,
        isWithShield: Int,
        isShot: Int,
        isDead: Int,
        id: Int,
        isWithJetpack: Boolean = false
    ): ObjectView {
        val bitmap = getBitmap(isDead, isShot, directionY)
        val rect = getRect(x, y, isDead, isShot)
        val matrix = getMatrix(directionX, rect, bitmap)

        var selectedShield: SelectedBonusView? = null
        var selectedJetpack: SelectedBonusView? = null

        if (isWithShield == TRUE) {
            selectedShield = selectedBonusViewFactory.getShieldView(x, y, directionX)
        }
        if (isWithJetpack) {
            selectedJetpack = selectedBonusViewFactory.getJetpackView(x, y, directionX)
        }

        return ObjectView(x, y, bitmap, matrix)
    }

    private fun getBitmap(isDead: Int, isShot: Int, directionY: Int): Bitmap {
        return if (isDead == TRUE) {
            deadPlayerBitmap
        } else if (isShot == TRUE) {
            shootPlayerBitmap
        } else if (directionY == DIRECTION_Y_UP) {
            jumpPlayerBitmap
        } else {
            idlePlayerBitmap
        }
    }

    private fun getMatrix(directionX: Int, destRect: RectF, bitmap: Bitmap): Matrix {
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

    private fun getRect(x: Float, y: Float, isDead: Int, isShot: Int): RectF {
        return if (isDead == TRUE) {
            RectF(x - DEAD_WIDTH / 2, y - DEAD_HEIGHT / 2, x + DEAD_WIDTH / 2, y + DEAD_HEIGHT / 2)
        } else if (isShot == TRUE) {
            RectF(
                x - SHOOTING_WIDTH / 2,
                y - SHOOTING_HEIGHT / 2,
                x + SHOOTING_WIDTH / 2,
                y + SHOOTING_HEIGHT / 2
            )
        } else {
            RectF(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS)
        }
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        const val DIRECTION_X_LEFT = -1
        const val DIRECTION_X_RIGHT = 1

        const val DIRECTION_Y_UP = -1
        const val DIRECTION_Y_DOWN = 1

        const val SHOOTING_WIDTH = 105f
        const val SHOOTING_HEIGHT = 200f

        private const val RADIUS = 75f

        private const val DEAD_WIDTH = 120f
        private const val DEAD_HEIGHT = 190f
    }
}