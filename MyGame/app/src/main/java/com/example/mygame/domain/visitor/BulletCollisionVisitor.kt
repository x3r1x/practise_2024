package com.example.mygame.domain.visitor

import android.graphics.RectF
import com.example.mygame.UI.GameSoundsPlayer
import com.example.mygame.domain.Enemy
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.platform.Platform
import com.example.mygame.domain.bonus.Jetpack
import com.example.mygame.domain.bonus.Shield
import com.example.mygame.domain.bonus.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.player.Player

class BulletCollisionVisitor(private val bullet: Bullet, private val audioPlayer: GameSoundsPlayer) : IVisitor {
    override fun visit(platform: Platform) {}
    override fun visit(player: Player) {}
    override fun visit(spring: Spring) {}
    override fun visit(shield: Shield) {}
    override fun visit(jetpack: Jetpack) {}

    override fun visit(enemy: Enemy) {
        if (!bullet.isDisappeared && !enemy.isDisappeared) {
            val bulletRect = RectF(bullet.left, bullet.top, bullet.right, bullet.bottom)
            val enemyRect = RectF(enemy.left, enemy.top, enemy.right, enemy.bottom)

            if (bulletRect.intersect(enemyRect)) {
                audioPlayer.playEnemyShotSound()

                enemy.isDisappeared = true
                bullet.isDisappeared = true
            }
        }
    }

    override fun visit(bullet: Bullet) {}
}