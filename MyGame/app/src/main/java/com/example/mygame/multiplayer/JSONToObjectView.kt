package com.example.mygame.multiplayer

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import com.example.mygame.domain.drawable.factory.BonusViewFactory
import com.example.mygame.domain.drawable.factory.BulletViewFactory
import com.example.mygame.domain.drawable.factory.EnemyViewFactory
import com.example.mygame.domain.drawable.factory.PlatformViewFactory
import com.example.mygame.domain.drawable.factory.PlayerViewFactory
import com.example.mygame.domain.drawable.view.FinishView
import com.example.mygame.domain.drawable.view.ObjectView

class JSONToObjectView(resources: Resources) {
    private val playerViewFactory = PlayerViewFactory(resources)
    private val platformViewFactory = PlatformViewFactory(resources)
    private val enemyViewFactory = EnemyViewFactory(resources)
    private val bonusViewFactory = BonusViewFactory(resources)
    private val bulletViewFactory = BulletViewFactory(resources)
    var count = 0

    fun getPlayerFromJSON(x: Float, y: Float, alpha: Int, data: Array<Double>) : ObjectView {
        val id = data[3].toInt()

        val directionX = data[4].toInt()
        val directionY = data[5].toInt()

        val isWinner = data[6].toInt()

        return playerViewFactory.getPlayerView(id, x, y, directionX, directionY, isWinner, alpha)
    }

    fun getPlatformFromJSON(data: Array<Double>) : ObjectView {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat()

        val id = data[3].toInt()

        val animationTime = data[4].toInt()

        return platformViewFactory.getPlatformView(id, type, posX, posY, animationTime)
    }

    fun getFinish(data: Array<Double>) : ObjectView {
        val posX = data[1].toFloat()
        val posY = data[2].toFloat()// + offset

        return FinishView(
            posX,
            posY,
            Paint().apply {
                color = Color.RED
            }
        )
    }

    fun getEnemyFromJSON(data: Array<Double>) : ObjectView {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat()

        val id = data[3].toInt()

        return enemyViewFactory.getEnemyView(type, posX, posY)
    }

    fun getBonusFromJSON(data: Array<Double>) : ObjectView {
        val type = data[0].toInt()

        val posX = data[1].toFloat()
        val posY = data[2].toFloat()

        val id = data[3].toInt()

        val animationTime = data[4].toInt()

        return bonusViewFactory.getBonusView(type, posX, posY, animationTime)
    }

    fun getBulletFromJSON(data: Array<Double>) : ObjectView {
        val posX = data[1].toFloat()
        val posY = data[2].toFloat()

        val id = data[3].toInt()

        return bulletViewFactory.getBulletView(posX, posY)
    }
}