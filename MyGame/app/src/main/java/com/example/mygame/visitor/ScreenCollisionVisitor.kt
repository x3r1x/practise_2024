package com.example.mygame.visitor

import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.platforms.MovingPlatformOnX

class ScreenCollisionVisitor(private val screen: Screen) : IVisitor {
    private fun isCollidePlayerWithScreen(player: Player) : Boolean {
        return player.right < screen.left || player.left > screen.right
    }

    private fun isCollidePlatformWithBoundsOfScreen(platform: Platform) : Boolean {
        return platform.right >= screen.right || platform.left <= screen.left
    }

    private fun isOutPlatformBelowBottomOfScreen(platform: Platform) : Boolean {
        return platform.top > screen.bottom + SIZE_OF_ALLOWED_ZONE_FOR_OBJECTS
    }

    override fun visit(player: Player) {
        if (isCollidePlayerWithScreen(player)) {
            player.movingThroughScreen(screen)
        }
    }

    override fun visit(platform: Platform) {
        if (platform is MovingPlatformOnX && isCollidePlatformWithBoundsOfScreen(platform)) {
            platform.changeDirectionX(screen)
        }

        if (isOutPlatformBelowBottomOfScreen(platform)) {
            platform.isDisappeared = true
        }
    }

    companion object {
        private const val SIZE_OF_ALLOWED_ZONE_FOR_OBJECTS = 200f
    }
}