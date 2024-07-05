package com.example.mygame.logic

import android.content.res.Resources
import android.util.Log
import com.example.mygame.factories.PlayerFactory
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.platforms.DisappearingPlatform

class ObjectsManager(
    private val resources: Resources,
    private val screen: Screen
) {
    val player = PlayerFactory(resources).generatePlayer()
    var objects = mutableListOf<IDrawable>()
    private val platformGenerator = PlatformGenerator(resources, screen.width, screen.height)

    fun initObjects() {
        player.setPosition(screen.width / 2f, screen.height - 800)

        objects.add(player)

        objects.addAll(platformGenerator.getPlatforms())
    }

    fun updateObjects() {
        platformGenerator.update()
        removeObjectsOutOfBounds(objects)

        generateObjects()
    }

    private fun generateObjects() {
        platformGenerator.generatePlatformsIfNeeded()

        objects = (mutableListOf(player) + platformGenerator.getPlatforms()).toMutableList()
        Log.i("", "objects: ${objects.size}")
    }

    private fun removeObjectsOutOfBounds(objects: MutableList<IDrawable>) {
        objects.retainAll(objects.filterNot {
            it is ICollidable && it !is Player && screen.collidesWith(it)
                    || (it is DisappearingPlatform && it.isDestroying)
        }.toMutableList())
    }
}