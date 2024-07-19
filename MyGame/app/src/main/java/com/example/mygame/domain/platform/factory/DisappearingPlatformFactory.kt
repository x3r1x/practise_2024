package com.example.mygame.domain.platform.factory

import com.example.mygame.domain.platform.DisappearingPlatform

class DisappearingPlatformFactory : IPlatformFactory {
    override fun generatePlatform(createdX: Float, createdY: Float): DisappearingPlatform {
        return DisappearingPlatform(createdX, createdY)
    }
}