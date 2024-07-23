package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.BulletViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.example.mygame.domain.drawable.view.ObjectView
import com.google.gson.Gson

class JSONToKotlin(resources: Resources) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)
    private val bulletViewFactory = BulletViewFactory(resources)

    private lateinit var gameData : GameData
    var objectsJSON = mutableListOf<IGameObjectJSON>()
    lateinit var playerJSON : PlayerJSON

    fun setGameState(jsonString: String) {
        gameData = parseJSON(jsonString)
        objectsJSON = mapObjectsFromJSON()
    }

    fun getObjectsViews() : List<ObjectView> {
        return mapObjectsViews()
    }

    fun interpolation() : List<ObjectView> {
        objectsJSON.forEach {
            it.x += it.speedX
            it.y += it.speedY
        }

        return mapObjectsViews()
    }

    private fun parseJSON(jsonString: String) : GameData {
        val gson = Gson()
        return gson.fromJson(jsonString, GameData::class.java)
    }

    private fun mapObjectsFromJSON() : MutableList<IGameObjectJSON> {
        val objects = mutableListOf<IGameObjectJSON>()

        val objectsFactory = JSONObjectFactory()
        gameData.objects.forEach {
            val type = (it[0] as Double).toInt()
            when (type) {
                ObjectType.PLAYER_TYPE -> objects.add(objectsFactory.getPlayerFromJSON(it))
                in ObjectType.MULTIPLAYER_PLATFORMS -> objects.add(objectsFactory.getPlatformFromJSON(it))
                in ObjectType.MULTIPLAYER_BONUSES -> objects.add(objectsFactory.getBonusFromJSON(it))
                in ObjectType.MULTIPLAYER_ENEMIES -> objects.add(objectsFactory.getEnemyFromJSON(it))
                ObjectType.BULLET_TYPE -> objects.add(objectsFactory.getBulletFromJSON(it))
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