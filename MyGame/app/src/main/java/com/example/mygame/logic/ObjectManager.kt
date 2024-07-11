package com.example.mygame.logic

import kotlin.math.abs
import android.util.Log
import android.content.res.Resources
import com.example.mygame.factory.BulletFactory
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.generator.LevelGenerator
import com.example.mygame.`object`.Bullet

class ObjectsManager(
    val resources: Resources,
    private val screen: Screen
) {
    val objectStorage = ObjectStorage(resources, screen)

    private val bulletFactory = BulletFactory(resources)

    private var tempScore = 0.0

    private val scoreThreshold = 4500.0

    private lateinit var levelGenerator: LevelGenerator

    fun initObjects() {
        levelGenerator = LevelGenerator(resources, screen)
        objectStorage.addAll(levelGenerator.generateInitialPack())
    }

    fun updateObjects(delta: Float) {
        objectStorage.setObjects(removeObjectsOutOfBounds())

        objectStorage.score.increase(delta)

        tempScore += abs(delta)

        if (tempScore >= scoreThreshold) {
            generateObjects()
            tempScore = 0.0
        }

        Log.i("objects size", "${objectStorage.getAll().size}")
    }

    fun getObjects() : List<IGameObject> {
        return objectStorage.getAll()
    }

    fun createBullet(touchX: Float, touchY: Float): Bullet {
        val player = objectStorage.getPlayer()
        val bullet = bulletFactory.generateBullet(player.x, player.y)
        bullet.shoot()
        objectStorage.addBullet(bullet)
        return bullet
    }

    private fun generateObjects() {
        val lastPlatform = getObjects().minBy { it.top }
        objectStorage.addAll(levelGenerator.generateNewPack(lastPlatform.top))
    }

    private fun removeObjectsOutOfBounds() : MutableList<IGameObject> {
        val objects = objectStorage.getAll()
        objects.retainAll(objects.filterNot {
            it !is Player && it.isDisappeared
        }.toList())

        return objects
    }
}