package com.example.mygame.logic

import android.content.res.Resources
import com.example.mygame.`object`.Score
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Platform
import com.example.mygame.`interface`.IBonus
import com.example.mygame.factory.PlayerFactory
import com.example.mygame.`interface`.IGameObject

class ObjectStorage(
    resources: Resources,
    screen: Screen
) {
    // TODO: враги, бонусы

    val score = Score()

    private val platforms = mutableListOf<Platform>()
    private val bonuses = mutableListOf<IBonus>()
    private var player = PlayerFactory(resources).generatePlayer()
    private val objects = mutableListOf<IGameObject>()

    init {
        player.setPosition(screen.width / 2f, screen.height - 800)
    }

    fun getPlayer() : Player {
        return player
    }

    fun getAll() : MutableList<IGameObject> {
        objects.clear()

        objects.addAll(platforms)
        objects.addAll(bonuses as MutableList<IGameObject>)
        // TODO: добавлять бонусы, врагов
        objects.add(player)
        objects.add(score)

        return objects
    }

    fun setObjects(list: List<IGameObject>) {
        if (list.isEmpty()) {
            return
        }

        platforms.clear()
        platforms.addAll(list.filterIsInstance<Platform>())

        bonuses.clear()
        bonuses.addAll(list.filterIsInstance<IBonus>())
        // TODO: очистка бонусов и врагов и их добавление
    }

    fun addAll(collection: MutableList<IGameObject>) {
        objects.addAll(collection)

        platforms.addAll(collection.filterIsInstance<Platform>())
        bonuses.addAll(collection.filterIsInstance<IBonus>())
        // TODO: добавлять бонусы в бонусы, врагов во врагов
    }
}