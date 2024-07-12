package com.example.mygame.domain.platform.factory

import com.example.mygame.R
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.domain.platform.MovingPlatformOnY
import com.example.mygame.domain.platform.factory.IPlatformFactory.Companion.BITMAP_OPTIONS

class MovingPlatformOnYFactory(
    private val resources: Resources
) : IPlatformFactory {
    private val platformImage = R.drawable.moving_platform_on_y

    override fun generatePlatform(createdX: Float, createdY: Float): MovingPlatformOnY {
        val bitmap = BitmapFactory.decodeResource(resources, platformImage, BITMAP_OPTIONS)

        return MovingPlatformOnY(bitmap, createdX, createdY)
    }
}