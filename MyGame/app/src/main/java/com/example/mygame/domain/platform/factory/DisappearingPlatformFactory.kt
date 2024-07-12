package com.example.mygame.domain.platform.factory

import com.example.mygame.R
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.domain.platform.DisappearingPlatform
import com.example.mygame.domain.platform.factory.IPlatformFactory.Companion.BITMAP_OPTIONS

class DisappearingPlatformFactory(private val resources: Resources) : IPlatformFactory {
    private val platformImage = R.drawable.disappearing_platform

    override fun generatePlatform(createdX: Float, createdY: Float): DisappearingPlatform {
        val bitmap = BitmapFactory.decodeResource(resources, platformImage, BITMAP_OPTIONS)

        return DisappearingPlatform(bitmap, createdX, createdY)
    }
}