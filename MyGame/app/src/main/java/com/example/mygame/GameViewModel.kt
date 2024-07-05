package com.example.mygame

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.logic.CollisionHandler
import com.example.mygame.logic.ObjectsManager
import com.example.mygame.logic.PositionHandler
import com.example.mygame.logic.SensorHandler
import com.example.mygame.`object`.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(private val application: Application) : AndroidViewModel(application), SensorHandler.SensorCallback {
    val gameObjects: LiveData<List<IDrawable>> get() = _gameObjects

    private var viewModelJob = Job()

    private var deltaX = 0f
    private var deltaY = 0f

    private val _gameObjects = MutableLiveData<List<IDrawable>>()

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
        _gameObjects.value = objectsManager.objects

        if (Physics().doWeNeedToMove(objectsManager.player, screen.maxPlayerHeight)) {
            PositionHandler(_gameObjects.value as List<IDrawable>)
                .screenPromotion(0f, Physics().moveOffset(objectsManager.player, screen.maxPlayerHeight))
            objectsManager.updateObjects()
        }

        collisionHandler.checkCollisions(objectsManager.player, screen, _gameObjects.value?.filterIsInstance<ICollidable>())

        PositionHandler(_gameObjects.value as List<IDrawable>).updatePositions(deltaX, elapsedTime)

        Log.i("object size", "${objectsManager.objects.size}")
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