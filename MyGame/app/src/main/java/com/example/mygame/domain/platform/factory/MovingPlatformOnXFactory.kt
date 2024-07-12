package com.example.mygame.domain.platform.factory

import com.example.mygame.R
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.domain.platform.MovingPlatformOnX
import com.example.mygame.domain.platform.factory.IPlatformFactory.Companion.BITMAP_OPTIONS

class MovingPlatformOnXFactory(private val resources: Resources) : IPlatformFactory {
    private val platformImage = R.drawable.moving_platform_on_x

    override fun generatePlatform(createdX: Float, createdY: Float): MovingPlatformOnX {
        val bitmap = BitmapFactory.decodeResource(resources, platformImage, BITMAP_OPTIONS)

        return MovingPlatformOnX(bitmap, createdX, createdY)
    }
}