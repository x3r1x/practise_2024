package com.example.mygame.domain.drawable.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.example.mygame.domain.bonus.Spring

class BonusView(
    x: Float,
    y: Float,
    override val bitmap: Bitmap,
    override val matrix: Matrix? = null,
    override val paint: Paint? = null,
    override val id: Int = -1,
    ) : ObjectView(x, y, bitmap, matrix, paint) {

    override val initialX = x
    override val initialY = y

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, x - Spring.WIDTH / 2, y - Spring.HEIGHT / 2, paint)
    }
}