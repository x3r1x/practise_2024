package com.example.mygame.`object`

import kotlin.math.abs
import android.util.Log
import android.graphics.RectF
import android.graphics.Paint
import android.graphics.Canvas
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.IGameObject

class Score: IGameObject, IDrawable {

    private var x = 100f
    private var y = 100f

    private var value: Double = 0.0

    private val paint = Paint().apply {
        textSize = 60f
    }

    override var isDisappeared = false

    override val left: Float
        get() = x - WIDTH

    override val right: Float
        get() = x + WIDTH
    override val top: Float
        get() = y - HEIGHT
    override val bottom: Float
        get() = y + HEIGHT

    fun increase(amount: Float) {
        value += abs(amount)
        Log.i("", "Score: $value")
    }

    override fun accept(visitor: IVisitor) {
        visitor.visit(this)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawText("${value.toInt() / 10}", x, y, paint)
    }

    companion object {
        private const val WIDTH = 100f
        private const val HEIGHT = 50f
    }
}