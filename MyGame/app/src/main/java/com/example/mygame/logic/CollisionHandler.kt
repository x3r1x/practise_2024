package com.example.mygame.logic

import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.platforms.BreakingPlatform
import com.example.mygame.`object`.platforms.DisappearingPlatform
import com.example.mygame.`object`.platforms.MovingPlatformOnX
import com.example.mygame.`object`.platforms.MovingPlatformOnY

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
                if (obj != player) {
                    obj.onScreenCollide(screen)
                }

                if (player != obj && player.collidesWith(obj)) {
                    // Реакция игрока на коллизию
                    player.onObjectCollide(obj)
                    // Соприкосновение игрока с сломанной платформой
                    if (obj is BreakingPlatform) {
                        obj.runDestructionAnimation(screen.height, { })
                    }
                    // Соприкосновение игрока с исчезающей платформой
                    if (obj is DisappearingPlatform) {
                        obj.animatePlatformColor({})
                    }
                }
                if (obj is MovingPlatformOnY) {
                    obj.updatePositionY(0f, 0f)
                }

                if (obj is MovingPlatformOnX) {
                    obj.updatePositionX(0f)
                }

            }
        }
    }
}