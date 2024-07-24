package com.example.mygame.domain.drawable.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.example.mygame.domain.Platform

class PlatformView(
    x: Float,
    y: Float,
    override val bitmap: Bitmap,
    override val matrix: Matrix,
    override val paint: Paint,
    override val id: Int,
) : ObjectView(x, y, bitmap, matrix, paint) {

    override fun draw(canvas: Canvas) {
        println("Draw platform at x: $x y: ${y}")
        canvas.drawBitmap(bitmap, x - Platform.WIDTH / 2, y - Platform.HEIGHT / 2, paint)
    }

}
