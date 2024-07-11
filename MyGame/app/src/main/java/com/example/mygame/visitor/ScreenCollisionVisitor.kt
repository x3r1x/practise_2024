package com.example.mygame.visitor

import android.graphics.RectF
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Bullet
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Score
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.bonuses.Jetpack
import com.example.mygame.`object`.bonuses.Shield
import com.example.mygame.`object`.bonuses.Spring
import com.example.mygame.`object`.platform.MovingPlatformOnX

class ScreenCollisionVisitor(private val screen: Screen) : IVisitor {
    override fun visit(player: Player) {
        if (isCollidePlayerWithScreen(player)) {
            player.movingThroughScreen(screen)
        }
    }

    override fun visit(score: Score) {
    }

    override fun visit(platform: Platform) {
        if (platform is MovingPlatformOnX && isCollidePlatformWithBoundsOfScreen(platform)) {
            platform.changeDirectionX(screen)
        }

        if (isOutPlatformBelowBottomOfScreen(platform)) {
            platform.isDisappeared = true
        }
    }

    override fun visit(spring: Spring) {}
    override fun visit(shield: Shield) {}
    override fun visit(jetpack: Jetpack) {}
    override fun visit(enemy: Enemy) {}

    override fun visit(bullet: Bullet) {
        if (screen.top > bullet.bottom) {
            bullet.isDisappeared = true
        }
    }

    private fun isCollidePlayerWithScreen(player: Player) : Boolean {
        return player.right < screen.left || player.left > screen.right
    }

    private fun isCollidePlatformWithBoundsOfScreen(platform: Platform) : Boolean {
        return platform.right >= screen.right || platform.left <= screen.left
    }

    private fun isOutPlatformBelowBottomOfScreen(platform: Platform) : Boolean {
        return platform.top > screen.bottom + SIZE_OF_ALLOWED_ZONE_FOR_OBJECTS
    }

    companion object {
        private const val SIZE_OF_ALLOWED_ZONE_FOR_OBJECTS = 200f
    }
}