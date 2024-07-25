package com.example.mygame.domain.logic

import com.example.mygame.UI.GameSoundsPlayer
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.player.Player
import com.example.mygame.domain.Screen
import com.example.mygame.domain.visitor.BulletCollisionVisitor
import com.example.mygame.domain.visitor.PlayerCollisionVisitor
import com.example.mygame.domain.visitor.ScreenCollisionVisitor

class CollisionHandler {
    fun checkCollisions(player: Player, screen: Screen, objects: List<IGameObject>, audioPlayer: GameSoundsPlayer) {
        checkScreenCollision(screen, objects)
        checkPlayerCollision(player, screen, objects, audioPlayer)
        checkBulletCollision(objects, audioPlayer)
    }

    private fun checkBulletCollision(objects: List<IGameObject>, audioPlayer: GameSoundsPlayer) {
        val bullet = objects.firstOrNull() { it::class == Bullet::class } as Bullet?

        if (bullet == null) {
            return
        }

        val bulletCollisionVisitor = BulletCollisionVisitor(bullet, audioPlayer)

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

    private fun checkPlayerCollision(player: Player, screen: Screen, objects: List<IGameObject>, audioPlayer: GameSoundsPlayer) {
        val playerCollisionVisitor = PlayerCollisionVisitor(player, screen.height, audioPlayer)

        objects.forEach {
            if (it !is Player) {
                it.accept(playerCollisionVisitor)
            }
        }
    }
}