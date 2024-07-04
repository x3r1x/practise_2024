package com.example.mygame.factories

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.`interface`.IPlatformFactory.Companion.BITMAP_OPTIONS
import com.example.mygame.`object`.platforms.MovingPlatformOnY

class MovingPlatformOnYFactory(
    private val resources: Resources,
    private val screenHeight: Float
) : IPlatformFactory {
    private val platformImage = R.drawable.moving_platform_on_y

    override fun generatePlatform(createdX: Float, createdY: Float): MovingPlatformOnY {
        val bitmap = BitmapFactory.decodeResource(resources, platformImage, BITMAP_OPTIONS)

        return MovingPlatformOnY(bitmap, createdX, createdY, screenHeight)
    }
}