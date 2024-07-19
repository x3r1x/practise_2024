package com.example.mygame.domain.drawable.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.example.mygame.UI.IDrawable

open class ObjectView(
    open val x: Float,
    open val y: Float,
    open val bitmap: Bitmap,
    open val matrix: Matrix,
    open val paint: Paint? = null
) : IDrawable {
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, matrix, paint)
    }
}