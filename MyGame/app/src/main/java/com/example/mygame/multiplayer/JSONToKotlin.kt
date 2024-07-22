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

    private lateinit var gameData : GameData

    fun getObjectsViews(jsonString: String) : List<ObjectView> {
        gameData = parseJSON(jsonString)

        return mapObjects(gameData)
    }

    fun interpolation() : List<ObjectView> {
        gameData.objects.forEach {
            it[1] = (it[1] as Double) + (it[3] as Double)
            it[2] = (it[2] as Double) + (it[4] as Double)
        }

        return mapObjects(gameData)
    }

    private fun parseJSON(jsonString: String) : GameData {
        val gson = Gson()
        return gson.fromJson(jsonString, GameData::class.java)
    }

    private fun mapObjects(gameData: GameData) : List<ObjectView> {
        val objectsViews = mutableListOf<ObjectView>()

        gameData.objects.forEach {
            val type = (it[0] as Double).toInt()
            when (type) {
                0 -> {
                    objectsViews.add(
                        playerViewFactory.getPlayerView(
                            (it[2] as Double).toFloat(),
                            (it[3] as Double).toFloat(),
                            (it[5] as Double).toFloat(),
                            (it[4] as Double).toFloat(),
                            (it[6] as Double).toInt(),
                            (it[7] as Double).toInt(),
                            (it[8] as Double).toInt(),
                            (it[9] as Double).toInt(),
                            (it[10] as Double).toInt()
                        )
                    )
                }

                in 1..5 -> {
                    objectsViews.add(
                        platformViewFactory.getPlatformView(
                            (it[0] as Double).toInt(),
                            (it[1] as Double).toFloat(),
                            (it[2] as Double).toFloat(),
                            (it[3] as Double).toFloat(),
                            (it[4] as Double).toFloat(),
                            (it[5] as Double).toInt()
                        )
                    )
                }

                in 6..7 -> {
                    objectsViews.add(
                        bonusViewFactory.getBonusView(
                            (it[0] as Double).toInt(),
                            (it[1] as Double).toFloat(),
                            (it[2] as Double).toFloat(),
                            (it[3] as Double).toFloat(),
                            (it[4] as Double).toFloat(),
                            (it[5] as Double).toInt()
                        )
                    )
                }

                in 8..10 -> {
                    objectsViews.add(
                        enemyViewFactory.getEnemyView(
                            (it[0] as Double).toInt(),
                            (it[1] as Double).toFloat(),
                            (it[2] as Double).toFloat(),
                            (it[3] as Double).toFloat(),
                            (it[4] as Double).toFloat()
                        )
                    )
                }

                11 -> {
                    objectsViews.add(
                        bulletViewFactory.getBulletView(
                            (it[1] as Double).toFloat(),
                            (it[2] as Double).toFloat(),
                            (it[3] as Double).toFloat(),
                            (it[4] as Double).toFloat()
                        )
                    )
                }
            }
        }

        return objectsViews
    }
}