package com.example.mygame

import kotlinx.coroutines.Job
import android.app.Application
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import com.example.mygame.`object`.Screen
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import com.example.mygame.logic.SensorHandler
import com.example.mygame.logic.ObjectsManager
import com.example.mygame.`interface`.IMoveable
import com.example.mygame.logic.PositionHandler
import com.example.mygame.logic.CollisionHandler
import com.example.mygame.`interface`.IGameObject
import com.example.mygame.logic.Score

class GameViewModel(private val application: Application) : AndroidViewModel(application), SensorHandler.SensorCallback {
    val gameObjects: LiveData<List<IGameObject>> get() = _gameObjects

    private var viewModelJob = Job()

    private var deltaX = 0f
    private var deltaY = 0f

    private val _gameObjects = MutableLiveData<List<IGameObject>>()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var screen: Screen

    private lateinit var sensorHandler: SensorHandler
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var objectsManager: ObjectsManager

    fun initialize(screenWidth: Float, screenHeight: Float) {
        this.screen = Screen(screenWidth, screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        collisionHandler = CollisionHandler()
        objectsManager = ObjectsManager(application.resources, screen)

        objectsManager.initObjects()
    }

    fun startGameLoop() {
        var elapsedTime: Float

        var startTime = System.currentTimeMillis()

        uiScope.launch {
            while (true) {
                val systemTime = System.currentTimeMillis()

                elapsedTime = (systemTime - startTime) / 1000f

                if (elapsedTime < MAX_FRAME_TIME) {
                    delay(1)
                    continue
                }

                startTime = systemTime

                updateGame(elapsedTime)
            }
        }
    }

    fun registerSensorHandler() {
        sensorHandler.register()
    }

    fun unregisterSensorHandler() {
        sensorHandler.unregister()
    }

    private fun updateGame(elapsedTime: Float) {
        _gameObjects.value = objectsManager.getObjects()

        if (Physics().doWeNeedToMove(objectsManager.objectStorage.getPlayer(), screen.maxPlayerHeight)) {
            val offsetY = Physics().moveOffset(objectsManager.objectStorage.getPlayer(), screen.maxPlayerHeight)
            PositionHandler(_gameObjects.value!!.filterIsInstance<IMoveable>())
                .screenScroll(0f, offsetY)
            objectsManager.updateObjects(offsetY)
        }

        collisionHandler.checkCollisions(objectsManager.objectStorage.getPlayer(), screen, _gameObjects.value!!)

        PositionHandler(_gameObjects.value!!.filterIsInstance<IMoveable>()).updatePositions(deltaX, elapsedTime)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        sensorHandler.unregister()
    }

    override fun onSensorDataChanged(deltaX: Float, deltaY: Float) {
        this.deltaX = deltaX
        this.deltaY = deltaY
    }

    companion object {
        private const val MAX_FRAME_TIME = 0.016f
    }
}