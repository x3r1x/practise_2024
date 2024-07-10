package com.example.mygame.logic

import android.util.Log
import android.content.res.Resources
import com.example.mygame.factories.bonuses.JetpackFactory
import com.example.mygame.factories.bonuses.ShieldFactory
import com.example.mygame.factories.bonuses.SpringFactory
import com.example.mygame.factories.platforms.StaticPlatformFactory
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`object`.bonuses.Jetpack

class ObjectsManager(
    val resources: Resources,
    private val screen: Screen
) {
    val objectStorage = ObjectStorage(resources, screen)

    private lateinit var platformGenerator: PlatformGenerator

    private var numberOfPlatformPacks = 3

    fun initObjects() {
        platformGenerator =
            PlatformGenerator(resources, screen.width, screen.height, objectStorage.getPlayer())

//        val entities: MutableList<IGameObject> = mutableListOf()
//
//        val platform1 = StaticPlatformFactory(resources).generatePlatform(500f, 2000f)
//        val platform2 = StaticPlatformFactory(resources).generatePlatform(500f, -700f)
//        val platform3 = StaticPlatformFactory(resources).generatePlatform(200f, -1000f)
//        val platform4 = StaticPlatformFactory(resources).generatePlatform(200f, -1700f)
//        val platform5 = StaticPlatformFactory(resources).generatePlatform(800f, -1500f)
//
//        val spring = SpringFactory(resources).generateBonus(platform1)
//        val shield = ShieldFactory(resources).generateBonus(platform3)
//        val jetpack = JetpackFactory(resources).generateBonus(platform5)
//
//        entities.add(platform1)
//        entities.add(platform2)
//        entities.add(platform3)
//        entities.add(platform4)
//        entities.add(platform5)
//
//        entities.add(spring)
//        entities.add(shield)
//        entities.add(jetpack)
//
//        objectStorage.addAll(entities)

        objectStorage.addAll(platformGenerator.generateInitialPlatforms().toMutableList())
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