package com.example.mygame.logic

import android.util.Log
import android.content.res.Resources
import com.example.mygame.generator.LevelGenerator
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.generator.PlatformGenerator

class ObjectsManager(
    private val resources: Resources,
    private val screen: Screen
) {
    val objectStorage = ObjectStorage(resources, screen)

    private lateinit var levelGenerator: LevelGenerator

    private var numberOfPlatformPacks = 2

    fun initObjects() {
        levelGenerator = LevelGenerator(resources, screen, objectStorage.getPlayer())
        objectStorage.addAll(levelGenerator.generateInitialPack())
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
        objectStorage.addAll(levelGenerator.generateNewPack())
    }

    private fun removeObjectsOutOfBounds() : MutableList<IGameObject> {
        val objects = objectStorage.getAll()
        objects.retainAll(objects.filterNot {
            it !is Player && it.isDisappeared
        }.toList())

        return objects
    }
}