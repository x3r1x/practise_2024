package com.example.mygame.domain.visitor

import com.example.mygame.domain.Enemy
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.Jetpack
import com.example.mygame.domain.bonuses.Shield
import com.example.mygame.domain.bonuses.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.enemies.Bully
import com.example.mygame.domain.platform.BreakingPlatform
import com.example.mygame.domain.platform.DisappearingPlatform
import com.example.mygame.domain.player.Player
import com.example.mygame.domain.player.Player.DirectionX
import com.example.mygame.domain.player.Player.DirectionY

class PlayerCollisionVisitor(
    private val player: Player,
    private val screenHeight: Float
) : IVisitor {

    override fun visit(platform: Platform) {
        if (doesPlayerCollideWithSolid(platform) && !player.isDead) {
            if (platform is BreakingPlatform) {
                platform.runDestructionAnimation(screenHeight)
                return
            } else if (platform is DisappearingPlatform) {
                platform.animatePlatformColor()
            }

            player.jump()
        }
    }

    override fun visit(player: Player) {
    }

    override fun visit(jetpack: Jetpack) {
        if (doesPlayerCollideWithCollectable(jetpack) && !player.isWithJetpack && !player.isDead) {
            jetpack.select(player)
        }
    }

    override fun visit(shield: Shield) {
        if (doesPlayerCollideWithCollectable(shield) && !player.isWithShield && !player.isDead) {
            shield.select(player)
        }
    }

    override fun visit(spring: Spring) {
        if (doesPlayerCollideWithSolid(spring) && !player.isDead) {
            spring.runStretchAnimation()
            player.jump(GameConstants.PLAYER_SPRING_JUMP_SPEED)
        }
    }

    override fun visit(enemy: Enemy) {
        if (checkCollisionWithEnemy(enemy) && !player.isDead) {
            if (player.directionY == DirectionY.UP && !player.isWithShield && !player.isWithJetpack) {
                enemy.killPlayer(player)
            } else {
                enemy.killEnemy()
                player.jump()
            }
        }
    }

    override fun visit(bullet: Bullet) {
    }

    private fun doesPlayerCollideWithSolid(other: IGameObject): Boolean {
        return if (player.isShooting) {
            (player.y + Player.SHOOTING_HEIGHT / 2 < other.bottom && player.y + Player.SHOOTING_HEIGHT / 2 >= other.top
                    && (player.x - Player.SHOOTING_WIDTH / 2 + 16f < other.right && player.x + Player.SHOOTING_WIDTH / 2 - 16f > other.left)
                    && player.directionY == DirectionY.DOWN)
        } else if (player.directionX == DirectionX.RIGHT) {
            (player.bottom < other.bottom && player.bottom >= other.top && player.directionY == DirectionY.DOWN
                    && (player.left + 15f < other.right && player.right - 50f > other.left))
        } else {
            (player.bottom < other.bottom && player.bottom >= other.top && player.directionY == DirectionY.DOWN
                    && (player.left + 50f < other.right && player.right - 15f > other.left))
        }
    }

    private fun doesPlayerCollideWithCollectable(other: IGameObject): Boolean {
        return (player.top < other.bottom && player.bottom >= other.top
                && (player.left < other.right && player.right > other.left))
    }

    private fun checkCollisionWithEnemy(other: IGameObject): Boolean {
        return if (other !is Bully) {
            (player.top < other.bottom && player.bottom >= other.top
                    && (player.left < other.right && player.right > other.left))
        } else {
            (player.top < other.bottom && player.bottom >= other.top
                    && (player.left < other.right - GameConstants.BULLY_DEATH_OFFSET_X
                    && player.right > other.left + GameConstants.BULLY_DEATH_OFFSET_X))
        }
    }
}
