package com.example.mygame.logic

import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.visitor.PlayerCollisionVisitor
import com.example.mygame.visitor.ScreenCollisionVisitor

class CollisionHandler {

    fun checkCollisions2(player: Player, screen: Screen, objects: List<IGameObject>) {
        checkCollisionScreenWithObjects(screen, objects)
        checkCollisionPlayerWithPlatform(player, screen, objects)
    }

    private fun checkCollisionScreenWithObjects(screen: Screen, objects: List<IGameObject>) {
        val screenCollisionVisitor = ScreenCollisionVisitor(screen)

        objects.forEach {
            it.accept(screenCollisionVisitor)
        }
    }

    private fun checkCollisionPlayerWithPlatform(player: Player, screen: Screen, objects: List<IGameObject>) {
        val playerCollisionVisitor = PlayerCollisionVisitor(player, screen.height)

        objects.forEach {
            if (it !is Player) {
                it.accept(playerCollisionVisitor)
            }
        }
    }
}