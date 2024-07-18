package com.example.mygame.domain.drawable

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.example.mygame.UI.IDrawable

class ObjectView(
    val x: Float,
    val y: Float,
    val bitmap: Bitmap,
    val matrix: Matrix,
    val paint: Paint? = null
) : IDrawable {
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, matrix, paint)
    }
}