package com.example.mygame.domain.drawable

import android.content.res.Resources
import com.example.mygame.UI.IDrawable
import com.example.mygame.domain.Enemy
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.platform.Platform
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
    val playerViewFactory = PlayerViewFactory(resources)
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
            player.x,
            player.y,
            0f,
            0f,
            player.directionX.value,
            player.directionY.value,
            player.isWithShield.toInt(),
            player.isShooting.toInt(),
            player.isDead.toInt(),
            player.isWithJetpack
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

        return platformViewFactory.getPlatformView(type, platform.x, platform.y, 0f, 0f, another)
    }

    private fun enemyToView(enemy: Enemy): ObjectView {
        var type = 0
        when (enemy) {
            is Bully -> type = ObjectType.BULLY_TYPE
            is Fly -> type = ObjectType.FLY_TYPE
            is Ninja -> type = ObjectType.NINJA_TYPE
        }

        return enemyViewFactory.getEnemyView(type, enemy.x, enemy.y, 0f, 0f)
    }

    private fun bonusToView(bonus: IBonus): ObjectView {
        val type: Int
        var another = 0

        when (bonus) {
            is Shield -> {
                type = ObjectType.SHIELD_TYPE
                return bonusViewFactory.getBonusView(type, bonus.x, bonus.y, 0f, 0f, another)
            }

            is Spring -> {
                type = ObjectType.SPRING_TYPE
                another = bonus.currentFrame
                return bonusViewFactory.getBonusView(type, bonus.x, bonus.y, 0f, 0f, another)
            }

            is Jetpack -> {
                type = ObjectType.JETPACK_TYPE
                return bonusViewFactory.getBonusView(type, bonus.x, bonus.y, 0f, 0f, another)
            }

            else -> throw IllegalArgumentException("Invalid bonus type")
        }
    }

    private fun bulletToView(bullet: Bullet): ObjectView {
        return bulletViewFactory.getBulletView(bullet.x, bullet.y, 0f, 0f)
    }

    private fun Boolean.toInt() = if (this) 1 else 0
}