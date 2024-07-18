package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.ObjectView
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.google.gson.Gson

class JSONToKotlin(resources: Resources) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)

    fun getObjects(jsonString: String) : List<ObjectView> {
        val gameData = parseJSON(jsonString)

        return mapObjects(gameData)
    }

    private fun parseJSON(jsonString: String) : GameData {
        val gson = Gson()
        return gson.fromJson(jsonString, GameData::class.java)
    }

    private fun mapObjects(gameData: GameData) : List<ObjectView> {
        val platforms = gameData.objects.platforms.map { platformJSON ->
            platformViewFactory.getPlatformView(platformJSON.x, platformJSON.y, platformJSON.typ, platformJSON.anm)
        }
        val enemies = gameData.objects.enemies.map { enemyJSON ->
            enemyViewFactory.getEnemyView(enemyJSON.x, enemyJSON.y, enemyJSON.typ)
        }
        val bonuses = gameData.objects.bonuses.map { bonusJSON ->
            bonusViewFactory.getBonusView(bonusJSON.x, bonusJSON.y, bonusJSON.typ, bonusJSON.anm)
        }
        val players = gameData.objects.players.map { playerJSON ->
            playerViewFactory.getPlayerView(playerJSON.x, playerJSON.y, playerJSON.stt, playerJSON.drx, playerJSON.dry)
        }

        return platforms + enemies + bonuses + players
    }
}