package com.example.mygame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.logic.CollisionHandler
import com.example.mygame.logic.SensorHandler
import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Screen
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class GameViewModel(application: Application) : AndroidViewModel(application), SensorHandler.SensorCallback {
    val gameObjects: LiveData<List<IDrawable>> get() = _gameObjects

    private var viewModelJob = Job()

    private var deltaX = 0f
    private var deltaY = 0f

    private var frameCount = 0
    private var startTime = System.currentTimeMillis()

    private var lastGeneratedPlatformY = 0f
    private var screenHeight by Delegates.notNull<Float>()

    private val _gameObjects = MutableLiveData<List<IDrawable>>()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val ball = Ball().apply { setPosition(275f, 1450f) }

    private lateinit var screen: Screen

    private lateinit var platforms: List<Platform>

    private lateinit var physics: Physics
    private lateinit var sensorHandler: SensorHandler
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var platformGenerator: PlatformGenerator

    fun initialize(screenWidth: Float, screenHeight: Float) {
        this.screenHeight = screenHeight

        physics = Physics(screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        collisionHandler = CollisionHandler()
        platformGenerator = PlatformGenerator(screenWidth, screenHeight)

        this.screen = Screen(screenWidth, screenHeight)
        platforms = platformGenerator.getPlatforms()

        lastGeneratedPlatformY = platforms.maxByOrNull { it.y }?.y ?: 0f
    }

    fun startGameLoop() {
        val frameRate = 750 / 60 //~12.5ms
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

    fun registerSensorHandler() {
        sensorHandler.register()
    }

    fun unregisterSensorHandler() {
        sensorHandler.unregister()
    }

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

        _gameObjects.value = listOf(ball) + platforms

        // Проверяем столкновения
        collisionHandler.checkCollisions(ball, screen, _gameObjects.value?.filterIsInstance<ICollidable>())
        ball.updatePosition(deltaX + deltaX * elapsedTime, deltaY + deltaY * elapsedTime)
        physics.movePlatforms(ball, platforms)

        // Обновляем список игровых объектов
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
}