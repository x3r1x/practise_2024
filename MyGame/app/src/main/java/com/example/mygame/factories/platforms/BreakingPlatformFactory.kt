package com.example.mygame.factories

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.mygame.R
import com.example.mygame.`interface`.IPlatformFactory
import com.example.mygame.`interface`.IPlatformFactory.Companion.BITMAP_OPTIONS
import com.example.mygame.`object`.platforms.BreakingPlatform

class BreakingPlatformFactory(resources: Resources) : IPlatformFactory {
    private val firstStateBitmap = BitmapFactory.decodeResource(resources, R.drawable.break_platform_1_state, BITMAP_OPTIONS)
    private val secondStateBitmap = BitmapFactory.decodeResource(resources, R.drawable.break_platform_2_state, BITMAP_OPTIONS)
    private val thirdStateBitmap = BitmapFactory.decodeResource(resources, R.drawable.break_platform_3_state, BITMAP_OPTIONS)
    private val fourthStateBitmap = BitmapFactory.decodeResource(resources, R.drawable.break_platform_4_state, BITMAP_OPTIONS)
    private val fifthStateBitmap = BitmapFactory.decodeResource(resources, R.drawable.break_platform_5_state, BITMAP_OPTIONS)

    override fun generatePlatform(createdX: Float, createdY: Float): BreakingPlatform {
        val bitmaps = mutableListOf(firstStateBitmap, secondStateBitmap, thirdStateBitmap, fourthStateBitmap, fifthStateBitmap)

        return BreakingPlatform(bitmaps, createdX, createdY)
    }
}