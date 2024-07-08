package com.example.mygame.logic

import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`object`.iteractable.Jetpack
import com.example.mygame.`object`.iteractable.Shield
import com.example.mygame.`object`.iteractable.Spring
import com.example.mygame.`object`.platforms.BreakingPlatform
import com.example.mygame.`object`.platforms.DisappearingPlatform

class CollisionHandler {
    fun checkCollisions(player: Player, screen: Screen, objects: List<ICollidable>?) {
        if (objects != null) {
            objects.forEachIndexed { index, firstObj ->
                checkCollisionObjectWithScreen(firstObj, screen)

                checkCollisionObjectWithPlayer(firstObj, player, screen)

                objects.drop(index + 1).forEach { secondObj ->
                    // Коллизии объектов одинакового типа нет
                    if (firstObj::class == secondObj::class) {
                        return@forEach
                    }
                    // TODO: проверка коллизии объектов разных типов
                }
            }
        }
    }

    private fun checkCollisionObjectWithScreen(obj: ICollidable, screen: Screen) {
        if (screen.collidesWith(obj)) {
            obj.onScreenCollide(screen)
        }
    }

    private fun checkCollisionObjectWithPlayer(obj: ICollidable, player: Player, screen: Screen) {
        if (obj != player && player.collidesWith(obj)) {
            if (obj is Jetpack || obj is Spring || obj is Shield) {
                obj.onObjectCollide(player)
            }

            if (obj is DisappearingPlatform) {
                obj.animatePlatformColor()
            }
            // TODO: подумать
            if (obj is BreakingPlatform) {
                obj.runDestructionAnimation(screen.height)
            } else {
                player.onObjectCollide(obj)
            }
        }
    }
}