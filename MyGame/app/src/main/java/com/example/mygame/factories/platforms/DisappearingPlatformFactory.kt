package com.example.mygame.factories

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.`interface`.IPlatformFactory.Companion.BITMAP_OPTIONS
import com.example.mygame.`object`.platforms.DisappearingPlatform

class DisappearingPlatformFactory(private val resources: Resources) : IPlatformFactory {
    private val platformImage = R.drawable.disappearing_platform

    override fun generatePlatform(createdX: Float, createdY: Float): DisappearingPlatform {
        val bitmap = BitmapFactory.decodeResource(resources, platformImage, BITMAP_OPTIONS)

        return DisappearingPlatform(bitmap, createdX, createdY)
    }
}