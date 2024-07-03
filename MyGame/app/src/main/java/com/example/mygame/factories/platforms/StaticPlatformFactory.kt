package com.example.mygame.factories

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.`interface`.IPlatformFactory.Companion.BITMAP_OPTIONS
import com.example.mygame.`object`.platforms.StaticPlatform

class StaticPlatformFactory(private val resources: Resources) : IPlatformFactory {
    private val staticPlatformImage = R.drawable.static_platform

    override fun generatePlatform(createdX: Float, createdY: Float): StaticPlatform {
        val bitmap = BitmapFactory.decodeResource(resources, staticPlatformImage, BITMAP_OPTIONS)

        return StaticPlatform(bitmap, createdX, createdY)
    }
}