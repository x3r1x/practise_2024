package com.example.mygame.logic

import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen

class CollisionHandler {
    fun checkCollisions(player: Player, screen: Screen, objects: List<ICollidable>?) {
        player.onScreenCollide(screen)
        //Высчитать позицию шара в следующем кадре
        //И если надо сдвинуть платформы
        //Проверить состояние шара
        //Предаём время, чтобы выявить коллизию в следующием кадре

        //СРАВНИВАЕМ ОБЪЕКТЫ КАЖДЫЙ С КАЖДЫМ!!!
        if (objects != null) {
            for (obj in objects) {
                if (player != obj && player.collidesWith(obj)) {
                    player.onObjectCollide(obj)
                }
            }
        }
    }
}