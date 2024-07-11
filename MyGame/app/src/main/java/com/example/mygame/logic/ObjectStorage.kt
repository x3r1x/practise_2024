package com.example.mygame.logic

import android.content.res.Resources
import com.example.mygame.`object`.Enemy
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

    private val platforms = mutableListOf<Platform>()
    private val bonuses = mutableListOf<IBonus>()
    private val enemies = mutableListOf<Enemy>()
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
        objects.addAll(enemies)
        objects.add(player)

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

        enemies.clear()
        enemies.addAll(list.filterIsInstance<Enemy>())

        // TODO: очистка бонусов и врагов и их добавление
    }

    fun addAll(collection: MutableList<IGameObject>) {
        objects.addAll(collection)

        platforms.addAll(collection.filterIsInstance<Platform>())
        bonuses.addAll(collection.filterIsInstance<IBonus>())
        enemies.addAll(collection.filterIsInstance<Enemy>())
        // TODO: добавлять бонусы в бонусы, врагов во врагов
    }
}