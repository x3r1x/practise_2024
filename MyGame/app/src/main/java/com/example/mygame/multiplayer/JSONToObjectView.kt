package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.BulletViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.example.mygame.domain.drawable.view.ObjectView

class JSONToObjectView(resources: Resources) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)
    private val bulletViewFactory = BulletViewFactory(resources)

    fun getPlayerFromJSON(data: Array<Double>) : ObjectView {
        val posX = data[1].toFloat()
        val posY = data[2].toFloat()

        val id = data[5].toInt()

        val directionX = data[6].toInt()
        val directionY = data[7].toInt()

        val isWithShield = data[8].toInt()
        val isShooting = data[9].toInt()
        val isDead = data[10].toInt()

        return playerViewFactory.getPlayerView(posX, posY, directionX, directionY, isWithShield, isShooting, isDead, id)
    }

    fun getPlatformFromJSON(data: Array<Double>, offset: Float) : ObjectView {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat()// + offset

        val id = data[5].toInt()

        val animationTime = data[6].toInt()

        return platformViewFactory.getPlatformView(type, posX, posY, animationTime)
    }

    fun getEnemyFromJSON(data: Array<Double>, offset: Float) : ObjectView {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + offset

        val id = data[5].toInt()

        return enemyViewFactory.getEnemyView(type, posX, posY)
    }

    fun getBonusFromJSON(data: Array<Double>, offset: Float) : ObjectView {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + offset

        val id = data[5].toInt()

        val animationTime = data[6].toInt()

        return bonusViewFactory.getBonusView(type, posX, posY, animationTime)
    }

    fun getBulletFromJSON(data: Array<Double>, offset: Float) : ObjectView {
        val posX = data[1].toFloat()
        val posY = data[2].toFloat() + offset

        val id = data[5].toInt()

        return bulletViewFactory.getBulletView(posX, posY)
    }
}