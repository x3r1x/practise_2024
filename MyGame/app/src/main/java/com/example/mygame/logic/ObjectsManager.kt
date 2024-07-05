package com.example.mygame.logic

import android.content.res.Resources
import com.example.mygame.factories.PlayerFactory
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.platforms.DisappearingPlatform

class ObjectsManager(
    resources: Resources,
    private val screen: Screen
) {
    val player = PlayerFactory(resources).generatePlayer()
    var objects = mutableListOf<IDrawable>()
    val platforms = mutableListOf<IDrawable>()
    //val enemies = mutableListOf<IDrawable>()
    //val boosters = mutableListOf<IDrawable>()


    fun initObjects() {
        player.setPosition(screen.width/2f, screen.height - 800)

        objects.add(player)
        generateObjects()
    }

    fun updateObjects() {
        removeObjectsOutOfBounds(objects)

        //generateObjects()
    }

    private val platformGenerator = PlatformGenerator(resources, screen.width, screen.height)

    private fun generateObjects() {
        platforms += platformGenerator.getPlatforms()
        // Генерация врагов, бустеров и тп, но с учетом score

        objects.addAll(platforms)
    }

    private fun removeObjectsOutOfBounds(objects: MutableList<IDrawable>) {
        objects.retainAll(objects.filterNot {
            it is ICollidable && it !is Player && screen.collidesWith(it)
                    || (it is DisappearingPlatform && it.isDestroying)
        }.toMutableList())
    }
}