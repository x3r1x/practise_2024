package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import com.example.mygame.R
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.drawable.factory.PlayerViewFactory.Companion.BITMAP_OPTIONS
import com.example.mygame.domain.drawable.factory.PlayerViewFactory.Companion.DIRECTION_X_LEFT

data class SelectedBonusView(
    val bitmap: Bitmap,
    val matrix: Matrix,
    val paint: Paint
)

class SelectedBonusViewFactory(resources: Resources) {
    private val jetpackOnPlayerBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.player_jetpack_left, BITMAP_OPTIONS)
    private val shieldOnPlayerBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.shield_on_player, BITMAP_OPTIONS)

    private var isShieldSelected = false
    private var isJetpackSelected = false

    private var timeWithShield = 0f
    private var shieldPulseTime = 0f

    private var timeWithJetpack = 0f
    private var jetpackPulseTime = 0f

    private val shieldPaint = Paint()
    private val jetpackPaint = Paint()

    fun getShieldView(x: Float, y: Float, directionX: Int): SelectedBonusView {
        if (!isShieldSelected) {
            selectShield()
        }

        val rect = RectF(
            x - shieldOnPlayerBitmap.width / 2,
            y - shieldOnPlayerBitmap.height / 2,
            x + shieldOnPlayerBitmap.width / 2,
            y + shieldOnPlayerBitmap.height / 2
        )
        val matrix = getMatrix(directionX, rect, shieldOnPlayerBitmap)

        return SelectedBonusView(shieldOnPlayerBitmap, matrix, shieldPaint)
    }

    fun getJetpackView(x: Float, y: Float, directionX: Int): SelectedBonusView {
        if (!isJetpackSelected) {
            selectJetpack()
        }

        val rect = getJetpackRect(x, y, directionX)
        val matrix = getMatrix(directionX, rect, jetpackOnPlayerBitmap)

        return SelectedBonusView(jetpackOnPlayerBitmap, matrix, jetpackPaint)
    }

    fun updateBonuses(additionalTime: Float) {
        if (isShieldSelected) {
            updateShieldTimer(additionalTime)
        }
        if (isJetpackSelected) {
            updateJetpackTimer(additionalTime)
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

    private fun getJetpackRect(x: Float, y: Float, directionX: Int): RectF {
        return when (directionX) {
            DIRECTION_X_LEFT -> RectF(
                x + JETPACK_OFFSET_ON_PLAYER - jetpackOnPlayerBitmap.width / 2,
                y - jetpackOnPlayerBitmap.height / 2,
                x + JETPACK_OFFSET_ON_PLAYER + jetpackOnPlayerBitmap.width / 2,
                y + jetpackOnPlayerBitmap.height / 2
            )

            else -> RectF(
                x - JETPACK_OFFSET_ON_PLAYER - jetpackOnPlayerBitmap.width / 2,
                y - jetpackOnPlayerBitmap.height / 2,
                x - JETPACK_OFFSET_ON_PLAYER + jetpackOnPlayerBitmap.width / 2,
                y + jetpackOnPlayerBitmap.height / 2
            )
        }
    }

    private fun selectShield() {
        isShieldSelected = true
        shieldPaint.alpha = GameConstants.SHIELD_DEFAULT_TRANSPARENCY
    }

    private fun selectJetpack() {
        isJetpackSelected = true
        jetpackPaint.alpha = GameConstants.JETPACK_DEFAULT_TRANSPARENCY
    }

    private fun updateShieldTimer(additionalTime: Float) {
        timeWithShield += additionalTime

        if (GameConstants.SHIELD_DURATION - timeWithShield <= 0f) {
            shieldPaint.alpha = 0
            isShieldSelected = false

            timeWithShield = 0f
            shieldPulseTime = 0f
        } else if (GameConstants.SHIELD_DURATION - timeWithShield <= GameConstants.WHEN_SHIELD_TO_PULSE) {
            if (shieldPulseTime == 0f) {
                shieldPaint.alpha = GameConstants.SHIELD_PULSE_TRANSPARENCY
            }

            if (shieldPulseTime >= GameConstants.SHIELD_TIMER_TICK) {
                shieldPaint.alpha = GameConstants.SHIELD_DEFAULT_TRANSPARENCY
            }

            shieldPulseTime += additionalTime

            if (shieldPulseTime >= GameConstants.SHIELD_TIMER_TICK * 2) {
                shieldPulseTime = 0f
            }
        }
    }

    private fun updateJetpackTimer(additionalTime: Float) {
        timeWithJetpack += additionalTime

        if (GameConstants.JETPACK_DURATION - timeWithJetpack <= 0f) {
            isJetpackSelected = false

            timeWithJetpack = 0f
            jetpackPulseTime = 0f
        } else if (GameConstants.JETPACK_DURATION - timeWithJetpack <= GameConstants.WHEN_JETPACK_TO_PULSE) {
            if (jetpackPulseTime == 0f) {
                jetpackPaint.alpha = GameConstants.JETPACK_PULSE_TRANSPARENCY
            }
            if (jetpackPulseTime >= GameConstants.JETPACK_TIMER_TICK) {
                jetpackPaint.alpha = GameConstants.JETPACK_DEFAULT_TRANSPARENCY
            }

            jetpackPulseTime += additionalTime

            if (jetpackPulseTime >= GameConstants.JETPACK_TIMER_TICK * 2) {
                jetpackPulseTime = 0f
            }
        }
    }

    companion object {
        private const val JETPACK_OFFSET_ON_PLAYER = 115f
    }
}