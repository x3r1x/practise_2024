package com.example.mygame.domain.visitor

import android.graphics.RectF
import com.example.mygame.domain.enemies.Enemy
import com.example.mygame.domain.IVisitor
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.Jetpack
import com.example.mygame.domain.bonuses.Shield
import com.example.mygame.domain.bonuses.Spring
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.player.Player

class BulletCollisionVisitor(private val bullet: Bullet) : IVisitor {
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
                bullet.isDisappeared = true
                enemy.isDisappeared = true
            }
        }
    }

    override fun visit(bullet: Bullet) {}
}