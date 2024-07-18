package com.example.mygame.multiplayer

import android.content.res.Resources
import com.google.gson.Gson

class Storage(resources: Resources) {
    private var gameData: GameData? = null
    private var objects: Objects? = null
    private var state: String? = null

    private val jsonParser = JSONToKotlin(resources)

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