package com.example.mygame.multiplayer

import com.google.gson.Gson

// TODO: Storage должен выдавать GameState какой-то, для отрисовки
// TODO: Создать класс в котором инициализируются фабрики всех объектов
// TODO: Он JSON превращает в kotlin классы

class Storage {
    private var gameData: GameData? = null
    private var objects: Objects? = null
    private var state: String? = null

    fun store(json: String) {
        gameData = parseJson(json)
        objects = gameData?.objects
        state = gameData?.state
    }

    fun getObjects(): Objects? {
        return objects
    }

    private fun parseJson(jsonString: String): GameData {
        val gson = Gson()
        val s = gson.fromJson(jsonString, GameData::class.java)
        return s
    }
}