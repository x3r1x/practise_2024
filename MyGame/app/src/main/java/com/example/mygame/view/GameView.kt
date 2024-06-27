package com.example.mygame.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.example.mygame.`interface`.Drawable

class GameView(context: Context, attributeSet: AttributeSet?): View(context, attributeSet) {

    private var gameElements: List<Drawable> = emptyList()

    fun drawGame(elements: List<Drawable>) {
        gameElements = elements
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gameElements.forEach {
            it.draw(canvas)
        }
    }
}