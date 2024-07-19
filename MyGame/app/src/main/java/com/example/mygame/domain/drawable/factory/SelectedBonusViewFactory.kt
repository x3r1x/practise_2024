package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.os.CountDownTimer
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

    private val shieldPaint = Paint()
    private val jetpackPaint = Paint()

    fun getShieldView(x: Float, y: Float, directionX: Int): SelectedBonusView {
        startShieldDisappearingTimer()
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
        startJetpackDisappearingTimer()
        val rect = getJetpackRect(x, y, directionX)
        val matrix = getMatrix(directionX, rect, jetpackOnPlayerBitmap)

        return SelectedBonusView(jetpackOnPlayerBitmap, matrix, jetpackPaint)
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

    private fun startShieldDisappearingTimer() {
        if (!isShieldSelected) {
            isShieldSelected = true
            shieldPaint.alpha = GameConstants.SHIELD_DEFAULT_TRANSPARENCY
            val timer = object :
                CountDownTimer(GameConstants.SHIELD_DURATION, GameConstants.SHIELD_TIMER_TICK) {
                override fun onTick(p0: Long) {
                    if (p0 <= GameConstants.WHEN_SHIELD_TO_PULSE) {
                        shieldPaint.alpha =
                            if (shieldPaint.alpha == GameConstants.SHIELD_PULSE_TRANSPARENCY) {
                                GameConstants.SHIELD_DEFAULT_TRANSPARENCY
                            } else {
                                GameConstants.SHIELD_PULSE_TRANSPARENCY
                            }
                    }
                }

                override fun onFinish() {
                    shieldPaint.alpha = 0
                    isShieldSelected = false
                }
            }
            timer.start()
        }
    }

    private fun startJetpackDisappearingTimer() {
        if (!isJetpackSelected) {
            isJetpackSelected = true
            jetpackPaint.alpha = GameConstants.JETPACK_DEFAULT_TRANSPARENCY
            val timer = object :
                CountDownTimer(GameConstants.JETPACK_DURATION, GameConstants.JETPACK_TIMER_TICK) {
                override fun onTick(p0: Long) {
                    if (p0 <= GameConstants.WHEN_JETPACK_TO_PULSE) {
                        jetpackPaint.alpha =
                            if (jetpackPaint.alpha == GameConstants.JETPACK_PULSE_TRANSPARENCY) {
                                GameConstants.JETPACK_DEFAULT_TRANSPARENCY
                            } else {
                                GameConstants.JETPACK_PULSE_TRANSPARENCY
                            }
                    }
                }

                override fun onFinish() {
                    isJetpackSelected = false
                }
            }
            timer.start()
        }
    }

    companion object {
        private const val JETPACK_OFFSET_ON_PLAYER = 115f
    }
}