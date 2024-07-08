package com.example.mygame.logic

import android.content.res.Resources
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.factories.PlayerFactory
import com.example.mygame.generator.LevelGenerator
import com.example.mygame.`object`.platforms.DisappearingPlatform

class ObjectsManager(
    resources: Resources,
    private val screen: Screen
) {
    var objects = mutableListOf<IDrawable>()
    val player = PlayerFactory(resources).generatePlayer()

    private val levelGenerator = LevelGenerator(resources, screen, player)

    private var numberOfPacks = 3

    fun initObjects() {
        player.setPosition(screen.width / 2f, screen.height - 800)

        objects.add(player)

        objects.addAll(levelGenerator.generateInitialPack())
    }

    fun updateObjects() {
        removeObjectsOutOfBounds(objects)

        if (numberOfPacks >= 0) {
            generateObjects()
            numberOfPacks--
        }
    }

    private fun generateObjects() {
        objects += levelGenerator.generateNewPack()
    }

    private fun removeObjectsOutOfBounds(objects: MutableList<IDrawable>) {
        objects.retainAll(objects.filterNot {
            it is ICollidable && it !is Player && it.top > screen.height
                    || (it is DisappearingPlatform && it.isDestroying)
        }.toMutableList())
    }
}