package com.example.mygame.domain.drawable.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.example.mygame.domain.platform.Platform

class PlatformView(
    x: Float,
    y: Float,
    override val bitmap: Bitmap,
    override val matrix: Matrix,
    override val paint: Paint,
    override val id: Int,
) : ObjectView(x, y, bitmap, matrix, paint) {

    override val initialX = x
    override val initialY = y

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, x - Platform.WIDTH / 2, y - Platform.HEIGHT / 2, paint)
    }

}
