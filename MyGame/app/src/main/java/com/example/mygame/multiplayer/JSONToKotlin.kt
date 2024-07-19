package com.example.mygame.multiplayer

import android.content.res.Resources
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

        gameData.objects.forEach {
            val obj = it
            val type = (obj[0] as Double).toInt()
            when (type) {
                0 -> {
                    objectsViews.add(
                        playerViewFactory.getPlayerView(
                            (obj[2] as Double).toFloat(),
                            (obj[3] as Double).toFloat(),
                            (obj[4] as Double).toFloat(),
                            (obj[5] as Double).toFloat(),
                            (obj[6] as Double).toInt(),
                            (obj[7] as Double).toInt(),
                            (obj[8] as Double).toInt(),
                            (obj[9] as Double).toInt(),
                            (obj[10] as Double).toInt()
                        )
                    )
                }

                in 1..5 -> {
                    objectsViews.add(
                        platformViewFactory.getPlatformView(
                            (obj[0] as Double).toInt(),
                            (obj[1] as Double).toFloat(),
                            (obj[2] as Double).toFloat(),
                            (obj[3] as Double).toFloat(),
                            (obj[4] as Double).toFloat(),
                            (obj[5] as Double).toInt()
                        )
                    )
                }

                in 6..7 -> {
                    objectsViews.add(
                        bonusViewFactory.getBonusView(
                            (obj[0] as Double).toInt(),
                            (obj[1] as Double).toFloat(),
                            (obj[2] as Double).toFloat(),
                            (obj[3] as Double).toFloat(),
                            (obj[4] as Double).toFloat(),
                            (obj[5] as Double).toInt()
                        )
                    )
                }

                in 8..10 -> {
                    objectsViews.add(
                        enemyViewFactory.getEnemyView(
                            (obj[0] as Double).toInt(),
                            (obj[1] as Double).toFloat(),
                            (obj[2] as Double).toFloat(),
                            (obj[3] as Double).toFloat(),
                            (obj[4] as Double).toFloat()
                        )
                    )
                }

                11 -> {
                    objectsViews.add(
                        bulletViewFactory.getBulletView(
                            (obj[1] as Double).toFloat(),
                            (obj[2] as Double).toFloat(),
                            (obj[3] as Double).toFloat(),
                            (obj[4] as Double).toFloat()
                        )
                    )
                }
            }
        }

        return objectsViews
    }
}