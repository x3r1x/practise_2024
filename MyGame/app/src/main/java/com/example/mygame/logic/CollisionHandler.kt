package com.example.mygame.logic

import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`object`.iteractable.Spring
import com.example.mygame.`object`.platforms.BreakingPlatform
import com.example.mygame.`object`.platforms.DisappearingPlatform

class CollisionHandler {
    fun checkCollisions(player: Player, screen: Screen, objects: List<ICollidable>?) {
        player.onScreenCollide(screen)
        //СРАВНИВАЕМ ОБЪЕКТЫ КАЖДЫЙ С КАЖДЫМ!!!

        if (objects != null) {
            for (obj in objects) {
                if (obj != player) {
                    obj.onScreenCollide(screen)
                }

                if (player != obj && player.collidesWith(obj)) {
                    //Соприкосновение игрока с пружиной
                    if (obj is Spring) {
                        obj.onObjectCollide(player)
                    }

                    // Соприкосновение игрока сo сломанной платформой
                    if (obj is BreakingPlatform) {
                        obj.runDestructionAnimation(screen.height, { }) // TODO: Убрать callback-функцию
                        continue
                    }

                    // Реакция игрока на коллизию
                    player.onObjectCollide(obj)

                    // Соприкосновение игрока с исчезающей платформой
                    if (obj is DisappearingPlatform) {
                        obj.animatePlatformColor({})
                    }
                }
            }
        }
    }
}