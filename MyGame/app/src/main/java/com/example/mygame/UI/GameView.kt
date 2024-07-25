package com.example.mygame.UI

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class GameView(context: Context, attributeSet: AttributeSet?): View(context, attributeSet) {
    private var gameElements: List<IDrawable> = emptyList()

    fun drawGame(elements: List<IDrawable>) {
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