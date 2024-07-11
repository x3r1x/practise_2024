package com.example.mygame.visitor

import android.graphics.RectF
import com.example.mygame.`interface`.IVisitor
import com.example.mygame.`object`.Bullet
import com.example.mygame.`object`.Enemy
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Score
import com.example.mygame.`object`.bonuses.Jetpack
import com.example.mygame.`object`.bonuses.Shield
import com.example.mygame.`object`.bonuses.Spring

class BulletCollisionVisitor(private val bullet: Bullet) : IVisitor {

    override fun visit(platform: Platform) {
        // Реализация для платформ
    }

    override fun visit(player: Player) {
        // Реализация для игрока
    }

    override fun visit(score: Score) {
        // Реализация для очков
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

            if (RectF.intersects(bulletRect, enemyRect)) {
                bullet.isDisappeared = true
                enemy.isDisappeared = true
            }
        }
    }

    override fun visit(bullet: Bullet) {
        // Реализация для пули
    }
}