package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.view.ObjectView
import com.google.gson.Gson

class JSONToKotlin(
    private val gson: Gson,
    resources: Resources,
//    private val score: Score,
//    private val objectsJSON: MutableList<IGameObjectJSON>,
    private val camera: Camera,
    private val objectsViews: MutableList<ObjectView>
) {
//    private val playerViewFactory = PlayerViewFactory(resources)
//    private val enemyViewFactory = EnemyViewFactory(resources)
//    private val platformViewFactory = PlatformViewFactory(resources)
//    private val bonusViewFactory = BonusViewFactory(resources)
//    private val bulletViewFactory = BulletViewFactory(resources)

    private val objectsViewFactory = JSONToObjectView(resources)

    private lateinit var playerView : ObjectView

    fun setGameState(jsonString: String) {
        val gameData = gson.fromJson(jsonString, GameData::class.java)
        //println("${gameData.objects[0][2]}")
        updateObjects(gameData)
    }

    fun getObjectsViews() : List<ObjectView> {
        objectsViews.forEach {
            it.y += camera.getOffsetY()
        }
        return objectsViews
    }

    private fun updateObjects(gameData: GameData) {
        if (objectsViews.isNotEmpty()) {
            val newObjectsViews = mapObjectsViewsFromJSON(gameData)
            newObjectsViews.forEach { newObject ->
                val existingObject = objectsViews.find { it.id == newObject.id }

                if (existingObject == null) {
                    objectsViews.add(newObject)
                } else {
                    val index = objectsViews.indexOf(existingObject)
                    objectsViews[index] = newObject
                    //objectsViews[index].updatePosition(newObject.x, newObject.y)
                    // TODO: другие обновления
                }
            }
//            objectsViews.removeAll { existingObject ->
//                existingObject is PlayerView && newObjectsViews.none { it.id == existingObject.id }
//            }
        } else {
            objectsViews.addAll(mapObjectsViewsFromJSON(gameData))
        }
        //println("${objectsViews[1].y}")
//        if (objectsJSON.isNotEmpty()) {
//            val newObjectsJSON = mapObjectsFromJSON(gameData)
//            newObjectsJSON.forEach { newObject ->
//                val existingObject = objectsJSON.find { it.id == newObject.id }
//
//                if (existingObject == null) {
//                    objectsJSON.add(newObject)
//                } else {
//                    objectsJSON[objectsJSON.indexOf(existingObject)] = newObject
//                }
//            }
//            objectsJSON.removeAll { existingObject ->
//                existingObject is PlayerJSON && newObjectsJSON.none { it.id == existingObject.id }
//            }
//        } else {
//            objectsJSON.addAll(mapObjectsFromJSON(gameData))
//        }
    }

    private fun mapObjectsViewsFromJSON(gameData: GameData) : MutableList<ObjectView> {
        val objects = mutableListOf<ObjectView>()
        var offsetY = 0f
        gameData.objects.forEach {
            val type = it[0].toInt()
            when (type) {
                ObjectType.PLAYER_TYPE -> {
                    playerView = objectsViewFactory.getPlayerFromJSON(it)

                    camera.countOffsetY(playerView.y)
                    offsetY = camera.getOffsetY()
                    playerView.y += offsetY

                    objects.add(playerView)
                }
                in ObjectType.MULTIPLAYER_PLATFORMS -> {
                    objects.add(objectsViewFactory.getPlatformFromJSON(it, offsetY))
                }
                in ObjectType.MULTIPLAYER_BONUSES -> objects.add(objectsViewFactory.getBonusFromJSON(it, offsetY))
                //in ObjectType.MULTIPLAYER_ENEMIES -> objects.add(objectsFactory.getEnemyFromJSON(it, offsetY))
                ObjectType.BULLET_TYPE -> objects.add(objectsViewFactory.getBulletFromJSON(it, offsetY))
            }
        }
        return objects
    }

//    private fun mapObjectsFromJSON(gameData: GameData) : MutableList<IGameObjectJSON> {
//        val objects = mutableListOf<IGameObjectJSON>()
//        var offsetY = 0f
//        //var offsetX = 0f
//
//        val objectsFactory = JSONObjectFactory()
//        gameData.objects.forEach {
//            val type = (it[0] as Double).toInt()
//            when (type) {
//                ObjectType.PLAYER_TYPE -> {
//                    playerJSON = objectsFactory.getPlayerFromJSON(it)
//                    camera.countOffsetY(playerJSON.y)
//                    offsetY = camera.getOffsetY()
//                    playerJSON.y += offsetY
//                    //println("${playerJSON.x} ${playerJSON.y} ${playerJSON.speedX} ${playerJSON.speedY}")
//                    objects.add(playerJSON)
//                }
//                in ObjectType.MULTIPLAYER_PLATFORMS -> objects.add(objectsFactory.getPlatformFromJSON(it, offsetY))
//                in ObjectType.MULTIPLAYER_BONUSES -> objects.add(objectsFactory.getBonusFromJSON(it, offsetY))
//                in ObjectType.MULTIPLAYER_ENEMIES -> objects.add(objectsFactory.getEnemyFromJSON(it, offsetY))
//                ObjectType.BULLET_TYPE -> objects.add(objectsFactory.getBulletFromJSON(it, offsetY))
//            }
//        }
//
//        return objects
//    }

//    private fun mapObjectsViews() : List<ObjectView> {
//        val objectsViews = mutableListOf<ObjectView>()
//        val offsetY = camera.getOffsetY()
//
//        objectsJSON.forEach {
//            when(it) {
//                is PlayerJSON -> objectsViews.add(playerViewFactory.getPlayerView(
//                    it.x, it.y, it.directionX, it.directionY, it.isWithShield, it.isShot, it.isDead
//                ))
//                is PlatformJSON -> objectsViews.add(platformViewFactory.getPlatformView(
//                    it.type, it.x, it.y + offsetY, it.animationTime
//                ))
//                is EnemyJSON -> objectsViews.add(enemyViewFactory.getEnemyView(
//                    it.type, it.x, it.y
//                ))
//                is BonusJSON -> objectsViews.add(bonusViewFactory.getBonusView(
//                    it.type, it.x, it.y, it.animationTime
//                ))
//                is BulletJSON -> objectsViews.add(bulletViewFactory.getBulletView(
//                    it.x, it.y
//                ))
//            }
//        }
//
//        return objectsViews
//    }
}