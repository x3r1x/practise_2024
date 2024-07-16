package com.example.mygame.domain.logic

import android.content.res.Resources
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.Screen
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.bullet.BulletFactory
import com.example.mygame.domain.generator.LevelGenerator
import com.example.mygame.domain.player.Player
import kotlin.math.abs

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

        tempScore += abs(delta)

        if (tempScore >= scoreThreshold) {
            generateObjects()
            tempScore = 0.0
        }
    }

    fun getObjects() : List<IGameObject> {
        return getUpdatedObjectsList()
    }

    fun createBullet(touchX: Float, touchY: Float): Bullet? {
        val player = objectStorage.getPlayer()
        if (!player.isDead && !player.isWithJetpack) {
            val bullet = bulletFactory.generateBullet(player.x, player.top, touchX, touchY)
            player.shoot()
            bullet.shoot()
            objectStorage.addBullet(bullet)
            return bullet
        }

        return null
    }

    private fun generateObjects() {
        val lastPlatform = getObjects().minBy { it.top }
        objectStorage.addAll(levelGenerator.generateNewPack(lastPlatform.top))
    }

    private fun getUpdatedObjectsList() : MutableList<IGameObject> {
        val objects = objectStorage.getAll()
        objects.retainAll(objects.filterNot {
            it !is Player && it.isDisappeared
        }.toList())
        return objects
    }
}