package com.example.mygame.domain.drawable.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.example.mygame.UI.IDrawable

open class ObjectView(
    var x: Float,
    var y: Float,
    open val bitmap: Bitmap? = null,
    open val matrix: Matrix? = null,
    open val paint: Paint? = null,
    open val id: Int = 0
) : IDrawable {
    open val initialX = x
    open val initialY = y

    override fun draw(canvas: Canvas) {
        bitmap?.let { bitmap ->
            matrix?.let { matrix ->
                canvas.drawBitmap(bitmap, matrix, paint)
            }
        }
    }

}
