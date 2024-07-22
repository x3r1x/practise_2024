package com.example.mygame.domain.platform.factory

import com.example.mygame.domain.platform.MovingPlatformOnX

class MovingPlatformOnXFactory() : IPlatformFactory {
    override fun generatePlatform(createdX: Float, createdY: Float): MovingPlatformOnX {
        return MovingPlatformOnX(createdX, createdY)
    }
}