package com.example.mygame.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.DrawableManager
import com.example.mygame.domain.gameplay.IGameplay
import com.example.mygame.domain.gameplay.SingleplayerGameplay
import com.example.mygame.domain.gameplay.MultiplayerGameplay
import com.example.mygame.domain.logic.CollisionHandler
import com.example.mygame.domain.logic.ObjectsManager
import com.example.mygame.domain.logic.PositionHandler
import com.example.mygame.domain.logic.SensorHandler

class GameViewModel(
    private val application: Application
) : AndroidViewModel(application), SensorHandler.SensorCallback {
    private lateinit var sensorHandler: SensorHandler
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var objectsManager: ObjectsManager
    private lateinit var positionHandler: PositionHandler
    private lateinit var screen: Screen
    private lateinit var drawableManager: DrawableManager

    enum class Type {
        SINGLEPLAYER,
        MULTIPLAYER
    }
    lateinit var gameplay: IGameplay

    fun initialize(screenWidth: Float, screenHeight: Float, initType: Type) {
        screen = Screen(screenWidth, screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        collisionHandler = CollisionHandler()
        objectsManager = ObjectsManager(screen)
        positionHandler = PositionHandler()
        drawableManager = DrawableManager(application.resources)

        if (initType == Type.SINGLEPLAYER) {
            gameplay = SingleplayerGameplay(objectsManager, sensorHandler, positionHandler, collisionHandler, drawableManager, screen)
            objectsManager.initObjects()
        } else  {
            gameplay = MultiplayerGameplay(application.resources, screen)
        }
    }

    override fun onSensorDataChanged(deltaX: Float) {
        gameplay.onSensorDataChanged(deltaX)
    }

    fun onClick(touchX: Float) {
        gameplay.onShot(touchX)
    }

    fun isGameLost() : Boolean {
        return objectsManager.objectStorage.getPlayer().top > screen.height
    }

    fun getScore(): Int {
        return gameplay.score.getScore()
    }
}