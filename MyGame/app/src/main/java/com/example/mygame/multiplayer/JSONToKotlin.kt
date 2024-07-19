package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.view.ObjectView
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.BulletViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.google.gson.Gson

class JSONToKotlin(resources: Resources) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)
    private val bulletViewFactory = BulletViewFactory(resources)

    fun getObjectsViews(jsonString: String) : List<ObjectView> {
        val gameData = parseJSON(jsonString)

        return mapObjects(gameData)
    }

    private fun parseJSON(jsonString: String) : GameData {
        val gson = Gson()
        return gson.fromJson(jsonString, GameData::class.java)
    }

    private fun mapObjects(gameData: GameData) : List<ObjectView> {
        val objectsViews = mutableListOf<ObjectView>()

        gameData.objects.data.forEach {
            when(it) {
                is PlayerJSON -> objectsViews.add(playerViewFactory.getPlayerView(
                    it.posX,
                    it.posY,
                    it.speedX,
                    it.speedY,
                    it.directionX,
                    it.directionY,
                    it.isWithShield,
                    it.isShooting,
                    it.isDead
                ))
                is PlatformJSON -> objectsViews.add(platformViewFactory.getPlatformView(
                    it.posX, it.posY, it.speedX, it.speedY, it.type, it.animationTime
                ))
                is EnemyJSON -> objectsViews.add(enemyViewFactory.getEnemyView(
                    it.posX, it.posY, it.speedX, it.speedY, it.type
                ))
                is BonusJSON -> objectsViews.add(bonusViewFactory.getBonusView(
                    it.posX, it.posY, it.speedX, it.speedY, it.type, it.animationTime
                ))
                is BulletJSON -> objectsViews.add(bulletViewFactory.getBulletView(
                    it.posX, it.posY, it.speedX, it.speedY
                ))
            }
        }

        return objectsViews
    }
}