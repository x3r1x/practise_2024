package com.example.mygame.domain.logic

import com.example.mygame.domain.Enemy
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.Platform
import com.example.mygame.domain.Screen
import com.example.mygame.domain.bonus.IBonus
import com.example.mygame.domain.bullet.Bullet
import com.example.mygame.domain.player.Player
import com.example.mygame.domain.player.PlayerFactory

class ObjectStorage(
    screen: Screen
) {
    // TODO: враги, бонусы

    private val platforms = mutableListOf<Platform>()
    private val bullets = mutableListOf<Bullet>()
    private val bonuses = mutableListOf<IBonus>()
    private val enemies = mutableListOf<Enemy>()
    private var player = PlayerFactory().generatePlayer()
    private val objects = mutableListOf<IGameObject>()

    init {
        player.setPosition(screen.width / 2f, screen.height - 800)
    }

    fun getPlayer() : Player {
        return player
    }

    fun addBullet(bullet: Bullet) {
        bullets.add(bullet)
    }

    fun getAll() : MutableList<IGameObject> {
        objects.clear()

        objects.addAll(platforms)
        objects.addAll(bonuses as MutableList<IGameObject>)
        objects.addAll(enemies)
        objects.addAll(bullets)
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

        bullets.clear()
        bullets.addAll(list.filterIsInstance<Bullet>())

        // TODO: очистка бонусов и врагов и их добавление
    }

    fun addAll(collection: MutableList<IGameObject>) {
        objects.addAll(collection)

        platforms.addAll(collection.filterIsInstance<Platform>())
        bonuses.addAll(collection.filterIsInstance<IBonus>())
        enemies.addAll(collection.filterIsInstance<Enemy>())
        bullets.addAll(collection.filterIsInstance<Bullet>())
        // TODO: добавлять бонусы в бонусы, врагов во врагов
    }
}