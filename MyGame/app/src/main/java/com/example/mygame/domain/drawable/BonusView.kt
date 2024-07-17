package com.example.mygame.domain.drawable

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.example.mygame.UI.IDrawable

class BonusView(
    val x: Float,
    val y: Float,
    val bitmap: Bitmap,
    val matrix: Matrix
) : IDrawable {
    override fun draw(canvas: Canvas) {
        TODO("Not yet implemented")
    }
}