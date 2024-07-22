package com.example.mygame.domain.platform.factory

import com.example.mygame.domain.platform.StaticPlatform

class StaticPlatformFactory : IPlatformFactory {
    override fun generatePlatform(createdX: Float, createdY: Float): StaticPlatform {
        return StaticPlatform(createdX, createdY)
    }
}