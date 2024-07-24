package com.example.mygame.domain.drawable

import android.content.res.Resources
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.Enemy
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonus.IBonus
import com.example.mygame.domain.bonus.Jetpack
import com.example.mygame.domain.bonus.Shield
import com.example.mygame.domain.bonus.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.BulletViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.example.mygame.domain.drawable.view.ObjectView
import com.example.mygame.domain.enemy.Bully
import com.example.mygame.domain.enemy.Fly
import com.example.mygame.domain.enemy.Ninja
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

    fun mapObjects(gameObjects: List<IGameObject>): List<IDrawable> {
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

    private fun playerToView(player: Player): ObjectView {
        return playerViewFactory.getPlayerView(
            x = player.x,
            y = player.y,
            directionX = player.directionX.value,
            directionY = player.directionY.value,
            isWithShield = player.isWithShield,
            isShot = player.isShooting,
            isDead = player.isDead,
            isWithJetpack = player.isWithJetpack
        )
    }

    private fun platformToView(platform: Platform): ObjectView {
        var type = 0
        var another = 0

        when (platform) {
            is StaticPlatform -> type = ObjectType.STATIC_PLATFORM_TYPE
            is MovingPlatformOnX -> type = ObjectType.MOVING_PLATFORM_ON_X_TYPE
            is MovingPlatformOnY -> type = ObjectType.MOVING_PLATFORM_ON_Y_TYPE
            is DisappearingPlatform -> {
                type = ObjectType.DISAPPEARING_PLATFORM_TYPE
                another = platform.platformColor.color
            }
            is BreakingPlatform -> {
                type = ObjectType.BREAKING_PLATFORM_TYPE
                another = platform.currentFrameIndex
            }
        }

        return platformViewFactory.getPlatformView(0, type, platform.x, platform.y, another)
    }

    private fun enemyToView(enemy: Enemy): ObjectView {
        var type = 0
        when (enemy) {
            is Bully -> type = ObjectType.BULLY_TYPE
            is Fly -> type = ObjectType.FLY_TYPE
            is Ninja -> type = ObjectType.NINJA_TYPE
        }

        return enemyViewFactory.getEnemyView(type, enemy.x, enemy.y)
    }

    private fun bonusToView(bonus: IBonus): ObjectView {
        val type: Int
        var another = 0

        when (bonus) {
            is Shield -> {
                type = ObjectType.SHIELD_TYPE
                return bonusViewFactory.getBonusView(type, bonus.x, bonus.y, another)
            }

            is Spring -> {
                type = ObjectType.SPRING_TYPE
                another = bonus.currentFrame
                return bonusViewFactory.getBonusView(type, bonus.x, bonus.y, another)
            }

            is Jetpack -> {
                type = ObjectType.JETPACK_TYPE
                return bonusViewFactory.getBonusView(type, bonus.x, bonus.y, another)
            }

            else -> throw IllegalArgumentException("Invalid bonus type")
        }
    }

    private fun bulletToView(bullet: Bullet): ObjectView {
        return bulletViewFactory.getBulletView(bullet.x, bullet.y)
    }

    private fun Boolean.toInt() = if (this) 1 else 0
}