package com.example.mygame.domain.visitor

import android.graphics.RectF
import com.example.mygame.UI.GameSoundsPlayer
import com.example.mygame.domain.enemies.Enemy
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.Jetpack
import com.example.mygame.domain.bonuses.Shield
import com.example.mygame.domain.bonuses.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.player.Player

class BulletCollisionVisitor(private val bullet: Bullet, private val audioPlayer: GameSoundsPlayer) : IVisitor {

    override fun visit(platform: Platform) {
        // Реализация для платформ
    }

    override fun visit(player: Player) {
        // Реализация для игрока
    }

    override fun visit(spring: Spring) {
        // Реализация для пружины
    }

    override fun visit(shield: Shield) {
        // Реализация для щита
    }

    override fun visit(jetpack: Jetpack) {
        // Реализация для реактивного ранца
    }

    override fun visit(enemy: Enemy) {
        // Если пуля сталкивается с врагом, выключаем врага
        if (!bullet.isDisappeared && !enemy.isDisappeared) {
            val bulletRect = RectF(bullet.left, bullet.top, bullet.right, bullet.bottom)
            val enemyRect = RectF(enemy.left, enemy.top, enemy.right, enemy.bottom)

            if (bulletRect.intersect(enemyRect)) {
                audioPlayer.playEnemyShotSound()

                bullet.isDisappeared = true
                enemy.isDisappeared = true
            }
        }
    }

    override fun visit(bullet: Bullet) {
        // Реализация для пули
    }
}