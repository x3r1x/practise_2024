package com.example.mygame.logic

import android.content.res.Resources
import android.util.Log
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen

class ObjectsManager(
    resources: Resources,
    private val screen: Screen
) {
    val objectStorage = ObjectStorage(resources, screen)

    private val platformGenerator = PlatformGenerator(resources, screen.width, screen.height)

    private var numberOfPlatformPacks = 2

    fun initObjects() {
        objectStorage.addAll(platformGenerator.generateInitialPlatforms() as MutableList<IGameObject>)
    }

    fun updateObjects() {
        objectStorage.setObjects(removeObjectsOutOfBounds())

        if (numberOfPlatformPacks >= 0) {
            generateObjects()
            numberOfPlatformPacks--
        }

        Log.i("objects size", "${objectStorage.getAll().size}")
    }
    fun getObjects() : List<IGameObject> {
        return objectStorage.getAll()
    }

    private fun generateObjects() {
        objectStorage.addAll(platformGenerator.generatePlatforms() as MutableList<IGameObject>)
    }

    private fun removeObjectsOutOfBounds() : MutableList<IGameObject> {
        val objects = objectStorage.getAll()
        objects.retainAll(objects.filterNot {
            it !is Player && it.isDisappeared
        }.toList())

        return objects
    }
}