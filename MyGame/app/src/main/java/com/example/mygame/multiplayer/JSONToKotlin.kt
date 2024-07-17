package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.ObjectView
import com.example.mygame.multiplayer.factory.BonusFactory
import com.example.mygame.multiplayer.factory.EnemyFactory
import com.example.mygame.multiplayer.factory.PlatformFactory
import com.example.mygame.multiplayer.factory.PlayerFactory
import com.google.gson.Gson

class JSONToKotlin(resources: Resources) {
    private val playerFactory = PlayerFactory(resources)
    private val enemyFactory = EnemyFactory(resources)
    private val platformFactory = PlatformFactory(resources)
    private val bonusFactory = BonusFactory(resources)

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
            platformFactory.getPlatformView(platformJSON)
        }
        val enemies = gameData.objects.enemies.map { enemyJSON ->
            enemyFactory.getEnemyView(enemyJSON)
        }
        val bonuses = gameData.objects.bonuses.map { bonusJSON ->
            bonusFactory.getBonusView(bonusJSON)
        }
        val players = gameData.objects.players.map { playerJSON ->
            playerFactory.getPlayerView(playerJSON)
        }

        return platforms + enemies + bonuses + players
    }
}