package com.example.mygame.logic

import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Bullet
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.visitor.BulletCollisionVisitor
import com.example.mygame.visitor.PlayerCollisionVisitor
import com.example.mygame.visitor.ScreenCollisionVisitor

class CollisionHandler {

    fun checkCollisions(player: Player, screen: Screen, objects: List<IGameObject>) {
        checkScreenCollision(screen, objects)
        checkPlayerCollision(player, screen, objects)
        checkBulletCollision(objects)
    }

    private fun checkBulletCollision(objects: List<IGameObject>) {
        val bullet = objects.firstOrNull() { it::class == Bullet::class } as Bullet?
        if (bullet == null) {
            return
        }

        val bulletCollisionVisitor = BulletCollisionVisitor(bullet)

        objects.forEach {
            it.accept(bulletCollisionVisitor)
        }
    }

    private fun checkScreenCollision(screen: Screen, objects: List<IGameObject>) {
        val screenCollisionVisitor = ScreenCollisionVisitor(screen)

        objects.forEach {
            it.accept(screenCollisionVisitor)
        }
    }

    private fun checkPlayerCollision(player: Player, screen: Screen, objects: List<IGameObject>) {
        val playerCollisionVisitor = PlayerCollisionVisitor(player, screen.height)

        objects.forEach {
            if (it !is Player) {
                it.accept(playerCollisionVisitor)
            }
        }
    }
}