package com.example.mygame.logic

import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.ICollidable

class CollisionHandler {
    fun checkCollisions(player: Player, screen: Screen, objects: List<ICollidable>?) {
        player.onScreenCollide(screen)
        //СРАВНИВАЕМ ОБЪЕКТЫ КАЖДЫЙ С КАЖДЫМ!!!

        if (objects != null) {
            for (first in objects) {
                for (second in objects)
                    if (first != second && first.collidesWith(second) == true) {
                        first.onObjectCollide(second)
                    }
            }
        }
    }
}