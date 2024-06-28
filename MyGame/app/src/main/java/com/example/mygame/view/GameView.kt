package com.example.mygame.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.example.mygame.R
import com.example.mygame.`interface`.Drawable

class GameView(context: Context, attributeSet: AttributeSet?): View(context, attributeSet) {

    private var gameElements: List<Drawable> = emptyList()
    private var backgroundImage: Bitmap? = null

    init {
        backgroundImage = BitmapFactory.decodeResource(resources, R.drawable.background)
    }

    fun drawGame(elements: List<Drawable>) {
        gameElements = elements
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        backgroundImage?.let { image ->
            canvas.drawBitmap(image, 0f, 0f, null)
        }
        gameElements.forEach {
            it.draw(canvas)
        }
    }
}