package com.example.mygame

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.generator.PlatformGenerator
import com.example.mygame.`interface`.ICollidable
import com.example.mygame.`interface`.IDrawable
import com.example.mygame.logic.CollisionHandler
import com.example.mygame.logic.ObjectsHandler
import com.example.mygame.logic.PositionHandler
import com.example.mygame.logic.SensorHandler
import com.example.mygame.`object`.Platform
import com.example.mygame.`object`.Player
import com.example.mygame.`object`.Screen
import com.example.mygame.`object`.platforms.MovingPlatformOnX
import com.example.mygame.`object`.platforms.MovingPlatformOnY
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

    private val ball = Player(
        BitmapFactory.decodeResource(getApplication<Application>().resources, R.drawable.player),
        BitmapFactory.decodeResource(getApplication<Application>().resources, R.drawable.jump)
    )

    private lateinit var screen: Screen

    private lateinit var platforms: List<Platform>
    private lateinit var objects: List<IDrawable>
    private lateinit var sensorHandler: SensorHandler
    private lateinit var collisionHandler: CollisionHandler
    private lateinit var objectsHandler: ObjectsHandler
    private lateinit var platformGenerator: PlatformGenerator

    fun initialize(screenWidth: Float, screenHeight: Float) {
        this.screen = Screen(screenWidth, screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        collisionHandler = CollisionHandler()
        objectsHandler = ObjectsHandler()
        platformGenerator = PlatformGenerator(application.resources, screenWidth, screenHeight)

        ball.setPosition(screen.width/2f, screen.height - 800)
        platforms = platformGenerator.getPlatforms()
        objects = listOf(ball) + platforms
    }

    fun startGameLoop() {
        var elapsedTime: Float

        var startTime = System.currentTimeMillis()

        uiScope.launch {
            while (true) {
                val systemTime = System.currentTimeMillis()

                elapsedTime = (systemTime - startTime) / 1000f

                if (elapsedTime < MAX_FRAME_TIME) {
                    continue
                }

                startTime = systemTime

                updateGame(elapsedTime)
                delay(1)
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
        //Log.d("", "Elapsed time: $elapsedTime")
        _gameObjects.value = objects

        if (Physics().doWeNeedToMove(ball, screen.maxPlayerHeight)) {
            PositionHandler(_gameObjects.value as List<IDrawable>)
                .updatePositions(0f, Physics().moveOffset(ball, screen.maxPlayerHeight))
            platformGenerator.generateNextPlatform()
        }

        ball.updatePositionX(deltaX + deltaX * elapsedTime)
        ball.updatePositionY(elapsedTime)

        objects.forEach {
            if (it is MovingPlatformOnY) {
                it.updatePositionY(elapsedTime) // TODO::: to elapsedTime
            }
            if (it is MovingPlatformOnX) {
                it.updatePositionX(0f)
            }
        }

        collisionHandler.checkCollisions(ball, screen, _gameObjects.value?.filterIsInstance<ICollidable>())
        objects = objectsHandler.updateObjects(screen, _gameObjects.value as List<IDrawable>)
        Log.i("list size", "${objects}")
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