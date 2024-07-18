package com.example.mygame.domain.drawable.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView

class EnemyViewFactory(private val resources: Resources) {
    private val bullyBitmap = BitmapFactory.decodeResource(resources, R.drawable.bully, BITMAP_OPTIONS)
    private val flyBitmap = BitmapFactory.decodeResource(resources, R.drawable.fly, BITMAP_OPTIONS)
    private val ninjaBitmap = BitmapFactory.decodeResource(resources, R.drawable.ninja, BITMAP_OPTIONS)

    fun getEnemyView(
        x: Float,
        y: Float,
        type: String
    ) : ObjectView {
        val bitmap = getBitmap(type)

        return ObjectView(x, y, bitmap, Matrix())
    }

    private fun getBitmap(type: String) : Bitmap {
        when (type) {
            BULLY -> return bullyBitmap
            FLY -> return flyBitmap
            NINJA -> return ninjaBitmap
            else -> throw IllegalArgumentException("Invalid type value: $type")
        }
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        const val BULLY = "bully"
        const val FLY = "fly"
        const val NINJA = "ninja"
    }
}