package com.example.mygame.domain.platform.factory

import com.example.mygame.domain.platform.BreakingPlatform

class BreakingPlatformFactory : IPlatformFactory {
    override fun generatePlatform(createdX: Float, createdY: Float): BreakingPlatform {
        return BreakingPlatform(createdX, createdY)
    }
}