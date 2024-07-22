package com.example.mygame.domain.visitor

import com.example.mygame.UI.GameSoundsPlayer
import com.example.mygame.domain.Enemy
import android.graphics.RectF
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonus.Jetpack
import com.example.mygame.domain.bonus.Shield
import com.example.mygame.domain.bonus.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.enemy.Bully
import com.example.mygame.domain.platform.BreakingPlatform
import com.example.mygame.domain.platform.DisappearingPlatform
import com.example.mygame.domain.player.Player
import com.example.mygame.domain.player.Player.DirectionY

class PlayerCollisionVisitor(
    private val player: Player,
    private val screenHeight: Float,
    private val audioPlayer: GameSoundsPlayer
) : IVisitor {
    override fun visit(platform: Platform) {
        if (doesPlayerCollideWithSolid(platform) && !player.isDead) {
            if (platform is BreakingPlatform) {
                platform.runDestructionAnimation(screenHeight)
                return
            } else if (platform is DisappearingPlatform) {
                platform.animatePlatformColor()
            }

            audioPlayer.playJumpSound()

            player.jump()
        }
    }

    override fun visit(player: Player) {}

    override fun visit(jetpack: Jetpack) {
        if (doesPlayerCollideWithCollectable(jetpack) && !player.isWithJetpack && !player.isDead) {
            jetpack.select(player, audioPlayer)
        }
    }

    override fun visit(shield: Shield) {
        if (doesPlayerCollideWithCollectable(shield) && !player.isWithShield && !player.isDead) {
            shield.select(player, audioPlayer)
        }
    }

    override fun visit(spring: Spring) {
        if (doesPlayerCollideWithSolid(spring) && !player.isDead) {
            audioPlayer.playSpringSound()

            spring.runStretchAnimation()
            player.jump(GameConstants.PLAYER_SPRING_JUMP_SPEED)
        }
    }

    override fun visit(enemy: Enemy) {
        if (checkCollisionWithEnemy(enemy) && !player.isDead) {
            if (player.directionY == DirectionY.UP && !player.isWithShield && !player.isWithJetpack) {
                audioPlayer.playPlayerKilledSound()

                enemy.killPlayer(player)
            } else {
                audioPlayer.playEnemySlainSound()

                enemy.killEnemy()
                player.jump()
            }
        }
    }

    override fun visit(bullet: Bullet) {}

    private fun doesPlayerCollideWithSolid(other: IGameObject) : Boolean {
        val playerFootRect = getPlayerFootCollisionRect(player)

        val otherRect = RectF(
            other.left,
            other.top,
            other.right,
            other.bottom
        )

        return (player.directionY == DirectionY.DOWN && playerFootRect.intersect(otherRect))
    }

    private fun doesPlayerCollideWithCollectable(other: IGameObject) : Boolean {
        val playerRect = getObjectRect(player)
        val otherRect = getObjectRect(other)

        return playerRect.intersect(otherRect)
    }

    private fun checkCollisionWithEnemy(other: IGameObject) : Boolean {
        val playerRect = getObjectRect(player)
        val otherRect: RectF

        if (other is Bully) {
            otherRect = RectF(
                other.left + GameConstants.BULLY_DEATH_OFFSET_X,
                other.top,
                other.right - GameConstants.BULLY_DEATH_OFFSET_X,
                other.bottom
            )
        } else {
            otherRect = getObjectRect(other)
        }

        return playerRect.intersect(otherRect)
    }

    private fun getObjectRect(obj: IGameObject) : RectF {
        return RectF(obj.left, obj.top, obj.right, obj.bottom)
    }

    private fun getPlayerFootCollisionRect(player: Player) : RectF {
        if (player.isShooting()) {
            return RectF(
                player.x + Player.SHOOTING_WIDTH / 2 - GameConstants.PLAYER_LEG_OFFSET_X_WHEN_SHOOT,
                player.y + Player.SHOOTING_HEIGHT / 2,
                player.x - Player.SHOOTING_WIDTH / 2 + GameConstants.PLAYER_LEG_OFFSET_X_WHEN_SHOOT,
                player.y + Player.SHOOTING_HEIGHT / 2
            )
        } else {
            when (player.directionX) {
                Player.DirectionX.RIGHT -> return RectF(
                    player.left + GameConstants.PLAYER_SMALL_LEG_OFFSET,
                    player.bottom,
                    player.right - GameConstants.PLAYER_BIG_LEG_OFFSET,
                    player.bottom
                )

                else -> return RectF(
                    player.left + GameConstants.PLAYER_BIG_LEG_OFFSET,
                    player.bottom,
                    player.right - GameConstants.PLAYER_SMALL_LEG_OFFSET,
                    player.bottom
                )
            }
        }
    }
}
