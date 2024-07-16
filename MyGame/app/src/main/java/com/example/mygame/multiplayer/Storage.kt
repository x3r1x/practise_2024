package com.example.mygame.multiplayer

import com.google.gson.Gson

class Storage {
    private var objects: GameData? = null

    fun store(json: String) {
        objects = parseJson(json)
    }

    fun get(): GameData? {
        return objects
    }

    private fun parseJson(jsonString: String): GameData {
        val gson = Gson()
        val s = gson.fromJson(jsonString, GameData::class.java)
        return s
    }
}