package com.example.mygame.logic

import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Screen

class CollisionHandler {
    fun checkCollisions(player: Ball, screen: Screen, objects: List<ICollidable>?) {
        player.screenBehaviour(screen)

        if (objects != null) {
            for (obj in objects) {
                if (player != obj && player.collidesWith(obj)) {
                    player.behaviour()
                }
            }
        }
    }
}