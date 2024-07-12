package com.example.mygame.domain.visitor

import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.Enemy
import com.example.mygame.domain.Platform
import com.example.mygame.domain.player.Player
import com.example.mygame.domain.Score
import com.example.mygame.domain.Screen
import com.example.mygame.domain.bonuses.Jetpack
import com.example.mygame.domain.bonuses.Shield
import com.example.mygame.domain.bonuses.Spring
import com.example.mygame.domain.platform.MovingPlatformOnX

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
        if (screen.top > bullet.top) {
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
        return platform.top > screen.bottom
    }
}