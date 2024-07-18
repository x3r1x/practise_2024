package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.ObjectView
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
        var platforms: List<ObjectView> = emptyList()
        var enemies: List<ObjectView> = emptyList()
        var bonuses: List<ObjectView> = emptyList()
        var bullets: List<ObjectView> = emptyList()
        var players: List<ObjectView> = emptyList()

        if (!gameData.objects.platforms.isEmpty()) {
            platforms = gameData.objects.platforms.map { platformJSON ->
                platformViewFactory.getPlatformView(platformJSON.x, platformJSON.y, platformJSON.typ, platformJSON.anm)
            }
        }
        if (!gameData.objects.enemies.isEmpty()) {
            enemies = gameData.objects.enemies.map { enemyJSON ->
                enemyViewFactory.getEnemyView(enemyJSON.x, enemyJSON.y, enemyJSON.typ)
            }
        }
        if (!gameData.objects.bonuses.isEmpty()) {
            bonuses = gameData.objects.bonuses.map { bonusJSON ->
                bonusViewFactory.getBonusView(bonusJSON.x, bonusJSON.y, bonusJSON.typ, bonusJSON.anm)
            }
        }
        if (!gameData.objects.bullets.isEmpty()) {
            bullets = gameData.objects.bullets.map { bulletJSON ->
                bulletViewFactory.getBulletView(bulletJSON.x, bulletJSON.y)
            }
        }
        if (!gameData.objects.players.isEmpty()) {
            players = gameData.objects.players.map { playerJSON ->
                val isDead = playerJSON.ded
                val isShot = playerJSON.sht
                val isWithShield = playerJSON.sld
                playerViewFactory.getPlayerView(playerJSON.x, playerJSON.y, playerJSON.drx, playerJSON.dry, isWithShield, isShot, isDead)
            }
        }

        return platforms + enemies + bonuses + bullets + players
    }
}