package com.example.mygame.domain.platform.factory

import com.example.mygame.domain.platform.MovingPlatformOnY

class MovingPlatformOnYFactory : IPlatformFactory {
    override fun generatePlatform(createdX: Float, createdY: Float): MovingPlatformOnY {
        return MovingPlatformOnY(createdX, createdY)
    }
}