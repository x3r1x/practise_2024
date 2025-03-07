package com.example.mygame.domain.drawable.view

import android.graphics.Canvas
import android.graphics.Paint

class FinishView(x: Float, y: Float, paint: Paint) : ObjectView(x, y, paint = paint) {

    override fun draw(canvas: Canvas) {
        paint?.let {
            canvas.drawRect(0f, y, 3000f, y + 20f, it)
            paint.textSize = 108f
            canvas.drawText("Finish", 24f, y - 20f, paint)
        }
    }

}
