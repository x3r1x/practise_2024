package com.example.mygame.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mygame.domain.Screen
import com.example.mygame.domain.gameplay.Gameplay
import com.example.mygame.domain.logic.CollisionHandler
import com.example.mygame.domain.logic.ObjectsManager
import com.example.mygame.domain.logic.PositionHandler
import com.example.mygame.domain.logic.SensorHandler

class GameViewModel(private val application: Application) : AndroidViewModel(application), SensorHandler.SensorCallback {
    private lateinit var sensorHandler: SensorHandler
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var objectsManager: ObjectsManager
    private lateinit var positionHandler: PositionHandler
    private lateinit var screen: Screen

    lateinit var gameplay: Gameplay

    fun initialize(screenWidth: Float, screenHeight: Float) {
        screen = Screen(screenWidth, screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        collisionHandler = CollisionHandler()
        objectsManager = ObjectsManager(application.resources, screen)
        positionHandler = PositionHandler()
        gameplay = Gameplay(objectsManager, sensorHandler, positionHandler, collisionHandler, screen)

        objectsManager.initObjects()
    }

    override fun onSensorDataChanged(deltaX: Float) {
        gameplay.setDeltaX(deltaX)
    }

    fun onClick(touchX: Float, touchY: Float) {
        gameplay.onShot(touchX, touchY)
    }

    fun isGameLost() : Boolean {
        return objectsManager.objectStorage.getPlayer().top > screen.height
    }

    fun getScore(): Int {
        return gameplay.score.getScore()
    }
}