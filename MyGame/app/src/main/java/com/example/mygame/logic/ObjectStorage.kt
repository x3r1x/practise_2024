package com.example.mygame.logic

import android.content.res.Resources
import com.example.mygame.factories.PlayerFactory
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.interactable.Jetpack
import com.example.mygame.`object`.interactable.Shield
import com.example.mygame.`object`.interactable.Spring

class ObjectStorage(
    resources: Resources,
    screen: Screen
) {
    // TODO: враги, бонусы
    private val platforms = mutableListOf<Platform>()

    private val springs = mutableListOf<Spring>()
    private val shields = mutableListOf<Shield>()
    private val jetpacks = mutableListOf<Jetpack>()

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
        // TODO: добавлять бонусы, врагов
        objects.addAll(springs)
        objects.addAll(jetpacks)

        objects.add(player)  //игрок обязательно рисуется после джетпаков и перед щитом!!!!

        objects.addAll(shields)

        return objects
    }

    fun setObjects(list: List<IGameObject>) {
        if (list.isEmpty()) {
            return
        }

        platforms.clear()
        platforms.addAll(list.filterIsInstance<Platform>())
        // TODO: очистка бонусов и врагов и их добавление
    }

    fun addAll(collection: MutableList<IGameObject>) {
        objects.addAll(collection)

        platforms.addAll(collection.filterIsInstance<Platform>())

        springs.addAll(collection.filterIsInstance<Spring>())
        shields.addAll(collection.filterIsInstance<Shield>())
        jetpacks.addAll(collection.filterIsInstance<Jetpack>())
        // TODO: добавлять бонусы в бонусы, врагов во врагов
    }
}