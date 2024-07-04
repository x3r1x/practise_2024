package com.example.mygame.factories.platforms

import com.example.mygame.R
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.`object`.platforms.StaticPlatform
import com.example.mygame.`interface`.IPlatformFactory.Companion.BITMAP_OPTIONS

class StaticPlatformFactory(private val resources: Resources) : IPlatformFactory {
    private val staticPlatformImage = R.drawable.static_platform

    override fun generatePlatform(createdX: Float, createdY: Float): StaticPlatform {
        val bitmap = BitmapFactory.decodeResource(resources, staticPlatformImage, BITMAP_OPTIONS)

        return StaticPlatform(bitmap, createdX, createdY)
    }
}