package com.example.mygame.domain.drawable.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.example.mygame.domain.drawable.factory.SelectedBonusView

class PlayerView(
    override var x: Float,
    override var y: Float,
    override val bitmap: Bitmap,
    override val matrix: Matrix,
    override val id: Int,
    val selectedShieldView: SelectedBonusView? = null,
    val selectedJetpackView: SelectedBonusView? = null
) : ObjectView(x, y, bitmap, matrix) {

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