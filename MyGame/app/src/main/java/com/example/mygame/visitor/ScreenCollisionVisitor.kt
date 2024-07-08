package com.example.mygame.visitor

import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.platforms.MovingPlatformOnX

class ScreenCollisionVisitor(private val screen: Screen) : IVisitor {
    override fun visit(player: Player) {
        if (IsCollidePlayerWithScreen(player)) {
            player.movingThroughScreen(screen)
        }
    }

    override fun visit(platform: Platform) {
        if (platform is MovingPlatformOnX && IsCollidePlatformWithBoundsOfScreen(platform)) {
            platform.changeDirectionX(screen)
        }

        if (IsOutPlatformBelowBottomOfScreen(platform)) {
            platform.isDisappeared = true
        }
    }

    private fun IsCollidePlayerWithScreen(player: Player) : Boolean {
        return player.right < screen.left || player.left > screen.right
    }

    private fun IsCollidePlatformWithBoundsOfScreen(platform: Platform) : Boolean {
        return platform.right >= screen.right || platform.left <= screen.left
    }

    private fun IsOutPlatformBelowBottomOfScreen(platform: Platform) : Boolean {
        return platform.top > screen.bottom + SIZE_OF_ALLOWED_ZONE_FOR_OBJECTS
    }

    companion object {
        private const val SIZE_OF_ALLOWED_ZONE_FOR_OBJECTS = 200f
    }
}