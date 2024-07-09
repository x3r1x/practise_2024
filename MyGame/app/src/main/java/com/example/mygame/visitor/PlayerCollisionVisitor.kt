package com.example.mygame.visitor

import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Platform
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Player.DirectionX
import com.example.mygame.`object`.Player.DirectionY
import com.example.mygame.`object`.bonuses.Jetpack
import com.example.mygame.`object`.bonuses.Shield
import com.example.mygame.`object`.bonuses.Spring
import com.example.mygame.`object`.platforms.BreakingPlatform
import com.example.mygame.`object`.platforms.DisappearingPlatform

class PlayerCollisionVisitor(
    private val player: Player,
    private val screenHeight: Float
) : IVisitor {

    override fun visit(platform: Platform) {
        if (doesPlayerCollideWithSolid(platform)) {
            if (platform is BreakingPlatform) {
                platform.runDestructionAnimation(screenHeight)
                return
            } else if (platform is DisappearingPlatform) {
                platform.animatePlatformColor()
            }

            player.jump()
        }
    }

    //player cannot interact with player
    override fun visit(player: Player) {}

    override fun visit(jetpack: Jetpack) {
        if (doesPlayerCollideWithCollectable(jetpack)) {
            jetpack.initPlayer(player)
            jetpack.startDisappearingTimer()
            jetpack.fly()
        }
    }

    override fun visit(shield: Shield) {
        if (doesPlayerCollideWithCollectable(shield)) {
            shield.initPlayer(player)
            shield.startDisappearingTimer()
        }
    }

    override fun visit(spring: Spring) {
        if (doesPlayerCollideWithSolid(spring)) {
            spring.runStretchAnimation()
            spring.throwPlayer(player)
            player.jump()
        }
    }

    override fun visit(enemy: Enemy) {
        TODO("Not yet implemented")
    }

    private fun doesPlayerCollideWithSolid(other: IGameObject) : Boolean {
        if (other !is Platform && other !is Spring) {
            return false
        }

        return if (player.directionX == DirectionX.RIGHT) {
            (player.bottom < other.bottom && player.bottom >= other.top && player.directionY == DirectionY.DOWN
                    && (player.left + 15f < other.right && player.right - 50f > other.left))
        } else {
            (player.bottom < other.bottom && player.bottom >= other.top && player.directionY == DirectionY.DOWN
                    && (player.left + 50f < other.right && player.right - 15f > other.left))
        }
    }

    private fun doesPlayerCollideWithCollectable(other: IGameObject) : Boolean {
        if (other !is Shield && other !is Jetpack) {
            return false
        }

        return if (player.directionX == DirectionX.RIGHT) {
            (player.bottom < other.bottom && player.bottom >= other.top
                    && (player.left < other.right && player.right > other.left))
        } else {
            (player.bottom < other.bottom && player.bottom >= other.top
                    && (player.left < other.right && player.right > other.left))
        }
    }
}