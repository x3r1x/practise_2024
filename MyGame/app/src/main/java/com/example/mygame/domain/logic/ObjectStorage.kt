package com.example.mygame.domain.logic

import android.content.res.Resources
import com.example.mygame.domain.enemies.Enemy
import com.example.mygame.domain.Screen
import com.example.mygame.domain.player.Player
import com.example.mygame.domain.Platform
import com.example.mygame.domain.bonuses.IBonus
import com.example.mygame.domain.player.PlayerFactory
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.bullet.Bullet

class ObjectStorage(
    resources: Resources,
    screen: Screen
) {
    private val platforms = mutableListOf<Platform>()
    private val bullets = mutableListOf<Bullet>()
    private val bonuses = mutableListOf<IBonus>()
    private val enemies = mutableListOf<Enemy>()
    private val objects = mutableListOf<IGameObject>()

    private var player = PlayerFactory(resources).generatePlayer()

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

    fun addAll(collection: MutableList<IGameObject>) {
        objects.addAll(collection)

        platforms.addAll(collection.filterIsInstance<Platform>())
        bonuses.addAll(collection.filterIsInstance<IBonus>())
        enemies.addAll(collection.filterIsInstance<Enemy>())
        bullets.addAll(collection.filterIsInstance<Bullet>())
    }
}