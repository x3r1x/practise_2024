package com.example.mygame.factories

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.`interface`.IPlatformFactory.Companion.BITMAP_OPTIONS
import com.example.mygame.`object`.platforms.MovingPlatformOnX

class MovingPlatformOnXFactory(private val resources: Resources) : IPlatformFactory {
    override fun generatePlatform(createdX: Float, createdY: Float): MovingPlatformOnX {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.moving_platform_on_x, BITMAP_OPTIONS)

        return MovingPlatformOnX(bitmap, createdX, createdY)
    }
}