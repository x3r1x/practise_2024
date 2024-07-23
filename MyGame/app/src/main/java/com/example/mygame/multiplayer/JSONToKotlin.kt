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
    private val objectsJSON: MutableList<IGameObjectJSON>
) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)
    private val bulletViewFactory = BulletViewFactory(resources)

    private val objectsFactory = JSONObjectFactory()

    lateinit var playerJSON : PlayerJSON

    fun setGameState(jsonString: String) {
        val gameData = parseJSON(jsonString)
        updateObjects(gameData)
    }

    fun getObjectsViews() : List<ObjectView> {
        return mapObjectsViews()
    }

    fun interpolation(elapsedTime: Float) : List<ObjectView> {
        objectsJSON.forEach {
            val deltaX = it.x - it.prevX
            val deltaY = it.y - it.prevY

            it.x += deltaX * elapsedTime
            it.y += deltaY * elapsedTime
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
                    val index = objectsJSON.indexOf(existingObject)
                    objectsJSON.set(index, objectsFactory.updateObject(existingObject, newObject))
                }
            }
        } else {
            objectsJSON.addAll(mapObjectsFromJSON(gameData))
        }
    }

    private fun parseJSON(jsonString: String) : GameData {
        return gson.fromJson(jsonString, GameData::class.java)
    }

    private fun mapObjectsFromJSON(gameData: GameData) : MutableList<IGameObjectJSON> {
        val objects = mutableListOf<IGameObjectJSON>()
        val scr = score.getScore().toFloat()

        gameData.objects.forEach {
            val type = (it[0] as Double).toInt()
            when (type) {
                ObjectType.PLAYER_TYPE -> {
                    playerJSON = objectsFactory.getPlayerFromJSON(it, scr)
                    println("${playerJSON.x} ${playerJSON.y} ${playerJSON.speedX} ${playerJSON.speedY}")
                    objects.add(playerJSON)
                }
                in ObjectType.MULTIPLAYER_PLATFORMS -> objects.add(objectsFactory.getPlatformFromJSON(it, scr))
                in ObjectType.MULTIPLAYER_BONUSES -> objects.add(objectsFactory.getBonusFromJSON(it, scr))
                in ObjectType.MULTIPLAYER_ENEMIES -> objects.add(objectsFactory.getEnemyFromJSON(it, scr))
                ObjectType.BULLET_TYPE -> objects.add(objectsFactory.getBulletFromJSON(it, scr))
            }
        }

        return objects
    }

    private fun mapObjectsViews() : List<ObjectView> {
        val objectsViews = mutableListOf<ObjectView>()

        objectsJSON.forEach {
            when(it) {
                is PlayerJSON -> objectsViews.add(playerViewFactory.getPlayerView(
                    it.x, it.y, it.directionX, it.directionY, it.isWithShield, it.isShot, it.isDead
                ))
                is PlatformJSON -> objectsViews.add(platformViewFactory.getPlatformView(
                    it.type, it.x, it.y, it.animationTime
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