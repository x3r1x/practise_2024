package com.example.mygame.logic

import android.util.Log
import android.content.res.Resources
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.factories.PlayerFactory
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.platforms.DisappearingPlatform

class ObjectsManager(
    private val resources: Resources,
    private val screen: Screen
) {
    var objects = mutableListOf<IDrawable>()
    val player = PlayerFactory(resources).generatePlayer()

    private lateinit var platforms: MutableList<Platform>

    private val platformGenerator = PlatformGenerator(resources, screen.width, screen.height)

    private var a = true

    fun initObjects() {
        player.setPosition(screen.width / 2f, screen.height - 800)

        objects.add(player)

        platforms = platformGenerator.generateInitialPlatforms()

        objects.addAll(platforms)
    }

    fun updateObjects() {
        removeObjectsOutOfBounds(objects)

        if (a) {
            generateObjects()
            a = false
        }

        Log.i("", "objects: ${objects.size}")
    }

    private fun generateObjects() {
        objects += platformGenerator.generatePlatforms()
    }

    private fun removeObjectsOutOfBounds(objects: MutableList<IDrawable>) {
        objects.retainAll(objects.filterNot {
            it is ICollidable && it !is Player && screen.collidesWith(it)
                    || (it is DisappearingPlatform && it.isDestroying)
        }.toMutableList())
    }
}