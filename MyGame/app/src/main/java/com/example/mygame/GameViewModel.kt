package com.example.mygame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`interface`.Drawable
import com.example.mygame.logic.CollisionHandler
import com.example.mygame.logic.SensorHandler
import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class GameViewModel(application: Application) : AndroidViewModel(application), SensorHandler.SensorCallback {
    private val _gameObjects = MutableLiveData<List<Drawable>>()
    val gameObjects: LiveData<List<Drawable>> get() = _gameObjects

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val ball = Ball().apply { setPosition(275f, 1450f) }

    private lateinit var platformGenerator: PlatformGenerator
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var sensorHandler: SensorHandler
    private lateinit var platforms: List<Platform>
    private lateinit var physics: Physics
    private var screenHeight by Delegates.notNull<Float>()
    private var lastGeneratedPlatformY = 0f

    private var deltaX = 0f
    private var deltaY = 0f

    fun initialize(screenWidth: Float, screenHeight: Float) {
        platformGenerator = PlatformGenerator(screenWidth, screenHeight)
        collisionHandler = CollisionHandler(screenWidth, screenHeight)
        physics = Physics(screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        platforms = platformGenerator.getPlatforms()

        this.screenHeight = screenHeight
        lastGeneratedPlatformY = platforms.maxByOrNull { it.y }?.y ?: 0f
    }

    fun startGameLoop() {
        val frameRate = 750 / 60 //~12.5ms = 60 FPS
        uiScope.launch {
            while (true) {
                val startTime = System.currentTimeMillis()

                countFrame()

                val elapsedTime = System.currentTimeMillis() - startTime

                updateGame(elapsedTime.toFloat()/1000)

                val delayTime = frameRate - elapsedTime
                if (delayTime > 0) {
                    delay(delayTime)
                }
            }
        }
    }

    private var frameCount = 0
    private var startTime = System.currentTimeMillis()

    private fun countFrame() {
        frameCount++
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - startTime

        if (elapsedTime >= 1000) {
            val fps = frameCount * 1000 / elapsedTime
            println("FPS: $fps")

            frameCount = 0
            startTime = currentTime
        }
    }

    private fun updateGame(elapsedTime: Float) {
        platforms = platformGenerator.getPlatforms()

        // Проверяем столкновения
        collisionHandler.checkCollisions(ball, platforms)
        ball.updatePosition(deltaX + deltaX * elapsedTime, deltaY + deltaY * elapsedTime)
        physics.movePlatforms(ball, platforms)

        // Обновляем список игровых объектов
        _gameObjects.value = listOf(ball) + platforms
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

    fun registerSensorHandler() {
        sensorHandler.register()
    }

    fun unregisterSensorHandler() {
        sensorHandler.unregister()
    }
}