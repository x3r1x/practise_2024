package com.example.mygame.domain.drawable

import android.content.res.Resources
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.Enemy
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.IBonus
import com.example.mygame.domain.bonuses.Jetpack
import com.example.mygame.domain.bonuses.Shield
import com.example.mygame.domain.bonuses.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.BulletViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.example.mygame.domain.enemies.Bully
import com.example.mygame.domain.enemies.Fly
import com.example.mygame.domain.enemies.Ninja
import com.example.mygame.domain.platform.BreakingPlatform
import com.example.mygame.domain.platform.DisappearingPlatform
import com.example.mygame.domain.platform.MovingPlatformOnX
import com.example.mygame.domain.platform.MovingPlatformOnY
import com.example.mygame.domain.platform.StaticPlatform
import com.example.mygame.domain.player.Player

class DrawableManager(resources: Resources) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)
    private val bulletViewFactory = BulletViewFactory(resources)

    fun mapObjects(gameObjects: List<IGameObject>) : List<IDrawable> {
        return gameObjects.map { gameObject ->
            when (gameObject) {
                is Player -> playerToView(gameObject)
                is Platform -> platformToView(gameObject)
                is IBonus -> bonusToView(gameObject)
                is Enemy -> enemyToView(gameObject)
                is Bullet -> bulletToView(gameObject)
                else -> throw IllegalArgumentException("Unknown game object type: ${gameObject::class}")
            }
        }
    }

    private fun playerToView(player: Player) : ObjectView {
        return playerViewFactory.getPlayerView(player.x, player.y, player.directionX.value, player.directionY.value, player.isWithShield, player.isShooting(), player.isDead)
    }

    private fun platformToView(platform: Platform) : ObjectView {
        var type = ""
        var another = 0

        when(platform) {
            is StaticPlatform -> type = PlatformViewFactory.STATIC
            is MovingPlatformOnX -> type = PlatformViewFactory.MOVING_ON_X
            is MovingPlatformOnY -> type = PlatformViewFactory.MOVING_ON_Y
            is DisappearingPlatform -> {
                type = PlatformViewFactory.DISAPPEARING
                another = platform.platformColor.color
            }
            is BreakingPlatform -> {
                type = PlatformViewFactory.BREAKING
                another = platform.currentFrameIndex
            }
        }

        return platformViewFactory.getPlatformView(platform.x, platform.y, type, another)
    }

    private fun enemyToView(enemy: Enemy) : ObjectView {
        var type = ""
        when(enemy) {
            is Bully -> type = EnemyViewFactory.BULLY
            is Fly -> type = EnemyViewFactory.FLY
            is Ninja -> type = EnemyViewFactory.NINJA
        }

        return enemyViewFactory.getEnemyView(enemy.x, enemy.y, type)
    }

    private fun bonusToView(bonus: IBonus) : ObjectView {
        var type: String
        var another = 0
        if (bonus is Shield) {
            type = BonusViewFactory.SHIELD
            if (bonus.isOnPlayer) {
                type = BonusViewFactory.SHIELD_ON_PLAYER
            }
            another = bonus.paint.alpha
            val coords = bonus.getCoords()
            return bonusViewFactory.getBonusView(coords.first, coords.second, type, another)
        } else if (bonus is Spring) {
            type = BonusViewFactory.SPRING
            another = bonus.currentFrame
            return bonusViewFactory.getBonusView(bonus.x, bonus.y, type, another)
        } else if (bonus is Jetpack) {
            if (bonus.state == Jetpack.State.ON_LEFT_OF_PLAYER) {
                type = BonusViewFactory.JETPACK_ON_PLAYER_LEFT
            } else if (bonus.state == Jetpack.State.ON_RIGHT_OF_PLAYER) {
                type = BonusViewFactory.JETPACK_ON_PLAYER_RIGHT
            } else {
                type = BonusViewFactory.JETPACK
            }
            val coords = bonus.getCoords()
            return bonusViewFactory.getBonusView(coords.first, coords.second, type, another)
        } else {
            throw IllegalArgumentException("Invalid bonus type")
        }
    }

    private fun bulletToView(bullet: Bullet) : ObjectView {
        return bulletViewFactory.getBulletView(bullet.x, bullet.y)
    }
}