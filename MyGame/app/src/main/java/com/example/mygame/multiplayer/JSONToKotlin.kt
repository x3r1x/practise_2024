package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.Score
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.BulletViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.example.mygame.domain.drawable.view.ObjectView
import com.google.gson.Gson

class JSONToKotlin(
    private val gson: Gson,
    resources: Resources,
    private val score: Score,
    private val objectsJSON: MutableList<IGameObjectJSON>,
    private val camera: Camera
) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)
    private val bulletViewFactory = BulletViewFactory(resources)

    lateinit var playerJSON : PlayerJSON

    fun setGameState(jsonString: String) {
        val gameData = gson.fromJson(jsonString, GameData::class.java)
        updateObjects(gameData)
    }

    fun getObjectsViews() : List<ObjectView> {
        return mapObjectsViews()
    }

    fun interpolation(elapsedTime: Float) : List<ObjectView> {
        objectsJSON.forEach {
            it.x += it.speedX * elapsedTime
            it.y += it.speedY * elapsedTime
        }

        return mapObjectsViews()
    }

    private fun updateObjects(gameData: GameData) {
        if (objectsJSON.isNotEmpty()) {
            val newObjectsJSON = mapObjectsFromJSON(gameData)
            newObjectsJSON.forEach { newObject ->
                val existingObject = objectsJSON.find { it.id == newObject.id }

                if (existingObject == null) {
                    objectsJSON.add(newObject)
                } else {
                    objectsJSON[objectsJSON.indexOf(existingObject)] = newObject
                }
            }
            objectsJSON.removeAll { existingObject ->
                existingObject is PlayerJSON && newObjectsJSON.none { it.id == existingObject.id }
            }
        } else {
            objectsJSON.addAll(mapObjectsFromJSON(gameData))
        }
    }

    private fun mapObjectsFromJSON(gameData: GameData) : MutableList<IGameObjectJSON> {
        val objects = mutableListOf<IGameObjectJSON>()
        var offsetY = 0f
        //var offsetX = 0f

        val objectsFactory = JSONObjectFactory()
        gameData.objects.forEach {
            val type = (it[0] as Double).toInt()
            when (type) {
                ObjectType.PLAYER_TYPE -> {
                    playerJSON = objectsFactory.getPlayerFromJSON(it)
                    camera.countOffsetY(playerJSON.y)
                    offsetY = camera.getOffsetY()
                    playerJSON.y += offsetY
                    //println("${playerJSON.x} ${playerJSON.y} ${playerJSON.speedX} ${playerJSON.speedY}")
                    objects.add(playerJSON)
                }
                in ObjectType.MULTIPLAYER_PLATFORMS -> objects.add(objectsFactory.getPlatformFromJSON(it, offsetY))
                in ObjectType.MULTIPLAYER_BONUSES -> objects.add(objectsFactory.getBonusFromJSON(it, offsetY))
                in ObjectType.MULTIPLAYER_ENEMIES -> objects.add(objectsFactory.getEnemyFromJSON(it, offsetY))
                ObjectType.BULLET_TYPE -> objects.add(objectsFactory.getBulletFromJSON(it, offsetY))
            }
        }

        return objects
    }

    private fun mapObjectsViews() : List<ObjectView> {
        val objectsViews = mutableListOf<ObjectView>()
        val offsetY = camera.getOffsetY()

        objectsJSON.forEach {
            when(it) {
                is PlayerJSON -> objectsViews.add(playerViewFactory.getPlayerView(
                    it.x, it.y, it.directionX, it.directionY, it.isWithShield, it.isShot, it.isDead
                ))
                is PlatformJSON -> objectsViews.add(platformViewFactory.getPlatformView(
                    it.type, it.x, it.y + offsetY, it.animationTime
                ))
                is EnemyJSON -> objectsViews.add(enemyViewFactory.getEnemyView(
                    it.type, it.x, it.y
                ))
                is BonusJSON -> objectsViews.add(bonusViewFactory.getBonusView(
                    it.type, it.x, it.y, it.animationTime
                ))
                is BulletJSON -> objectsViews.add(bulletViewFactory.getBulletView(
                    it.x, it.y
                ))
            }
        }

        return objectsViews
    }
}