package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.view.ObjectView
import com.example.mygame.domain.drawable.view.PlayerView
import com.google.gson.Gson

class JSONToKotlin(
    resources: Resources,
    private val gson: Gson,
    private val offset: Offset,
    private val objectsViews: MutableList<ObjectView>
) {
    var id: Int? = null

    private val objectsViewFactory = JSONToObjectView(resources)

    fun setGameState(jsonString: String) {
        val newObjectsViews = mapObjectsViewsFromJSON(jsonString)
        val isFirstLoad = objectsViews.isEmpty()

        if (isFirstLoad) {
            objectsViews.addAll(newObjectsViews)
            return
        }

        newObjectsViews.forEach { newObject ->
            val existingObjectIndex =
                objectsViews.indexOfFirst { it.id == newObject.id }

            if (existingObjectIndex == -1) {
                objectsViews.add(newObject)
            } else {
                objectsViews[existingObjectIndex] = newObject
            }
        }

        objectsViews.forEach {
            if (it is PlayerView) {
                return@forEach
            }
            it.x = it.initialX + offset.getX()
            it.y = it.initialY + offset.getY()
        }
    }

    private fun mapObjectsViewsFromJSON(jsonString: String): List<ObjectView> {
        val gameData = gson.fromJson(jsonString, GameData::class.java)

        return gameData.objects.mapNotNull {
            when (it[0].toInt()) {
                ObjectType.PLAYER_TYPE -> {
                    val posX = it[1].toFloat()
                    val posY = it[2].toFloat()
                    val alpha : Int

                    if (it[3].toInt() == id) {
                        offset.calcFrom(posX, posY)
                        alpha = GameConstants.CURRENT_PLAYER_TRANSPARENCY
                    } else {
                        alpha = GameConstants.OTHER_PLAYER_TRANSPARENCY
                    }

                    objectsViewFactory.getPlayerFromJSON(posX + offset.getX(), posY + offset.getY(), alpha, it)
                }

                ObjectType.FINISH_LINE_TYPE ->
                    objectsViewFactory.getFinish(it)

                in ObjectType.MULTIPLAYER_PLATFORMS ->
                    objectsViewFactory.getPlatformFromJSON(it)

                in ObjectType.MULTIPLAYER_BONUSES ->
                    objectsViewFactory.getBonusFromJSON(it)

                else -> null
            }
        }
    }
}