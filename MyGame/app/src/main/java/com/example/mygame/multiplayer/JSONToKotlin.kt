package com.example.mygame.multiplayer

import android.content.res.Resources
import com.example.mygame.domain.drawable.ObjectType
import com.example.mygame.domain.drawable.view.ObjectView
import com.google.gson.Gson

class JSONToKotlin(
    resources: Resources,
    private val gson: Gson,
    private val camera: Camera,
    private val objectsViews: MutableList<ObjectView>
) {
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
    }

    private fun mapObjectsViewsFromJSON(jsonString: String): List<ObjectView> {
        val gameData = gson.fromJson(jsonString, GameData::class.java)
        var offsetY = 0f

        return gameData.objects.mapNotNull {
            return@mapNotNull when (it[0].toInt()) {
                ObjectType.PLAYER_TYPE -> {
                    val playerView = objectsViewFactory.getPlayerFromJSON(it)

                    camera.countOffsetY(playerView.y)
                    offsetY = camera.getOffsetY()
                    println("offsetY: $offsetY")
                    playerView.y += offsetY

                    playerView
                }

                in ObjectType.MULTIPLAYER_PLATFORMS ->
                    objectsViewFactory.getPlatformFromJSON(it, offsetY)

                in ObjectType.MULTIPLAYER_BONUSES ->
                    objectsViewFactory.getBonusFromJSON(it, offsetY)

                else -> null
            }
        }
    }
}