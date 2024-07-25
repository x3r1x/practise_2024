package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.ObjectType.Companion.TRUE
import com.example.mygame.domain.drawable.view.ObjectView
import com.example.mygame.domain.drawable.view.PlayerView
import com.example.mygame.domain.gameplay.Type
import com.google.gson.Gson

class JSONToKotlin(
    resources: Resources,
    private val gson: Gson,
    private val offset: Offset,
    private val objectsViews: MutableList<ObjectView>
) {
    var id: Int? = null

    var winnerId = 0

    var type = Type.LOBBY

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
            objectsViews.removeAll { existingObject ->
                existingObject is PlayerView && newObjectsViews.none { it.id == existingObject.id }
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
        updateGameType(gameData.state)

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

                    if (it[6].toInt() == TRUE) {
                        winnerId = it[3].toInt()
                    }

                    objectsViewFactory.getPlayerFromJSON(posX + offset.getX(), posY + offset.getY(), alpha, it)
                }

                ObjectType.FINISH_LINE_TYPE ->
                    objectsViewFactory.getFinish(it)

                in ObjectType.MULTIPLAYER_PLATFORMS ->
                    objectsViewFactory.getPlatformFromJSON(it)

                ObjectType.SPRING_TYPE -> objectsViewFactory.getBonusFromJSON(it)

                else -> null
            }
        }
    }

    private fun updateGameType(type: String) {
        if (type != this.type.value) {
            when (type) {
                Type.GAME.value -> this.type = Type.GAME
                Type.LOBBY.value -> this.type = Type.LOBBY
                Type.END.value -> this.type = Type.END
            }
        }
    }
}