package com.example.mygame.domain.platform.factory

import com.example.mygame.R
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.domain.platform.StaticPlatform
import com.example.mygame.domain.platform.factory.IPlatformFactory.Companion.BITMAP_OPTIONS

class StaticPlatformFactory(private val resources: Resources) : IPlatformFactory {
    private val staticPlatformImage = R.drawable.static_platform

    override fun generatePlatform(createdX: Float, createdY: Float): StaticPlatform {
        val bitmap = BitmapFactory.decodeResource(resources, staticPlatformImage, BITMAP_OPTIONS)

        return StaticPlatform(bitmap, createdX, createdY)
    }
}