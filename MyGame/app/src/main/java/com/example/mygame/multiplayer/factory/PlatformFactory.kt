package com.example.mygame.multiplayer.factory

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.mygame.R
import com.example.mygame.domain.drawable.ObjectView
import com.example.mygame.domain.platform.factory.IPlatformFactory
import com.example.mygame.domain.platform.factory.IPlatformFactory.Companion
import com.example.mygame.multiplayer.PlatformJSON

class PlatformFactory(private val resources: Resources) {
    private val staticPlatformImage = R.drawable.static_platform
    private val movingOnYPlatformImage = R.drawable.moving_platform_on_y
    private val movingOnXPlatformImage = R.drawable.moving_platform_on_x
    private val disappearingPlatformImage = R.drawable.disappearing_platform
    private val breakingPlatformImages = listOf(
        R.drawable.break_platform_1_state,
        R.drawable.break_platform_2_state,
        R.drawable.break_platform_3_state,
        R.drawable.break_platform_4_state,
        R.drawable.break_platform_5_state
    )

    fun getPlatformView(platformJSON: PlatformJSON) : ObjectView {
        val x = platformJSON.x
        val y = platformJSON.y
        val type = platformJSON.typ
        val animation = platformJSON.anm

        val bitmap = getBitmap(type, animation)

        return ObjectView(x, y, bitmap, Matrix())
    }

    private fun getBitmap(type: String, animation: Int) : Bitmap {
        when(type) {
            STATIC -> return BitmapFactory.decodeResource(resources, staticPlatformImage, BITMAP_OPTIONS)
            DISAPPEARING -> return BitmapFactory.decodeResource(resources, disappearingPlatformImage, BITMAP_OPTIONS)
            MOVING_ON_X -> return BitmapFactory.decodeResource(resources, movingOnXPlatformImage, BITMAP_OPTIONS)
            MOVING_ON_Y -> return BitmapFactory.decodeResource(resources, movingOnYPlatformImage, BITMAP_OPTIONS)
            else -> when (animation) {
                0 -> return BitmapFactory.decodeResource(resources, breakingPlatformImages[0], BITMAP_OPTIONS)
                1 -> return BitmapFactory.decodeResource(resources, breakingPlatformImages[1], BITMAP_OPTIONS)
                2 -> return BitmapFactory.decodeResource(resources, breakingPlatformImages[2], BITMAP_OPTIONS)
                3 -> return BitmapFactory.decodeResource(resources, breakingPlatformImages[3], BITMAP_OPTIONS)
                4 -> return BitmapFactory.decodeResource(resources, breakingPlatformImages[4], BITMAP_OPTIONS)
                else -> throw IllegalArgumentException("Invalid animation value: $animation")
            }
        }
    }

    companion object {
        val BITMAP_OPTIONS = BitmapFactory.Options().apply {
            inScaled = false
        }

        private const val STATIC = "static"
        private const val BREAKING = "breaking"
        private const val DISAPPEARING = "disappearing"
        private const val MOVING_ON_X = "movingOnX"
        private const val MOVING_ON_Y = "movingOnY"
    }
}