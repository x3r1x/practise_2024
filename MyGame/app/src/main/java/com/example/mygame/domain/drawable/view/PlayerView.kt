package com.example.mygame.domain.drawable.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.example.mygame.domain.drawable.factory.SelectedBonusView

class PlayerView(
    x: Float,
    y: Float,
    override val bitmap: Bitmap,
    override val matrix: Matrix,
    override val id: Int,
    private val selectedShieldView: SelectedBonusView? = null,
    private val selectedJetpackView: SelectedBonusView? = null
) : ObjectView(x, y, bitmap, matrix) {
    override val initialX = x
    override val initialY = y

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (selectedShieldView != null) {
            canvas.drawBitmap(
                selectedShieldView.bitmap,
                selectedShieldView.matrix,
                selectedShieldView.paint
            )
        }
        if (selectedJetpackView != null) {
            canvas.drawBitmap(
                selectedJetpackView.bitmap,
                selectedJetpackView.matrix,
                selectedJetpackView.paint
            )
        }
        canvas.drawBitmap(bitmap, matrix, paint)
    }

}
