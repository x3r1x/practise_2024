package com.example.mygame.factory.platform

import com.example.mygame.R
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.`object`.platform.BreakingPlatform
import com.example.mygame.`interface`.IPlatformFactory.Companion.BITMAP_OPTIONS

class BreakingPlatformFactory(resources: Resources) : IPlatformFactory {
    private val platformImages = listOf(
        R.drawable.break_platform_1_state,
        R.drawable.break_platform_2_state,
        R.drawable.break_platform_3_state,
        R.drawable.break_platform_4_state,
        R.drawable.break_platform_5_state
    )

    private val firstStateBitmap = BitmapFactory.decodeResource(resources, platformImages[0], BITMAP_OPTIONS)
    private val secondStateBitmap = BitmapFactory.decodeResource(resources, platformImages[1], BITMAP_OPTIONS)
    private val thirdStateBitmap = BitmapFactory.decodeResource(resources, platformImages[2], BITMAP_OPTIONS)
    private val fourthStateBitmap = BitmapFactory.decodeResource(resources, platformImages[3], BITMAP_OPTIONS)
    private val fifthStateBitmap = BitmapFactory.decodeResource(resources, platformImages[4], BITMAP_OPTIONS)

    override fun generatePlatform(createdX: Float, createdY: Float): BreakingPlatform {
        val bitmaps = mutableListOf(firstStateBitmap, secondStateBitmap, thirdStateBitmap, fourthStateBitmap, fifthStateBitmap)

        return BreakingPlatform(bitmaps, createdX, createdY)
    }
}