package com.example.mygame.domain.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.domain.GameConstants
import com.example.mygame.domain.IGameObject
import com.example.mygame.domain.IMoveable
import com.example.mygame.domain.Physics
import com.example.mygame.domain.Score
import com.example.mygame.domain.Screen
import com.example.mygame.domain.drawable.DrawableManager
import com.example.mygame.domain.logic.CollisionHandler
import com.example.mygame.domain.logic.ObjectsManager
import com.example.mygame.domain.logic.PositionHandler
import com.example.mygame.domain.logic.SensorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Gameplay(
    private val objectsManager: ObjectsManager,
    private val sensorHandler: SensorHandler,
    private val positionHandler: PositionHandler,
    private val collisionHandler: CollisionHandler,
    private val drawableManager: DrawableManager,
    private val screen: Screen
) : IGameplay {
    private var isGameLoopRunning = false

    private var deltaX = 0f
    val score = Score()

    private val uiScope = CoroutineScope(Dispatchers.Main)

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState
    private val gameObjects = MutableLiveData<List<IGameObject>>()

    private val _scoreObservable = MutableLiveData<Int>()
    val scoreObservable: LiveData<Int> = _scoreObservable

    fun setDeltaX(newX: Float) {
        deltaX = newX
    }

    fun startGameLoop() {
        isGameLoopRunning = true
        gameLoop()
    }

    fun stopGameLoop() {
        isGameLoopRunning = false
    }

    private fun gameLoop() {
        var elapsedTime: Float

        var startTime = System.currentTimeMillis()

        uiScope.launch {
            while (isGameLoopRunning) {
                _scoreObservable.value = score.getScore()
                val systemTime = System.currentTimeMillis()

                elapsedTime = (systemTime - startTime) / 1000f

                if (elapsedTime < GameConstants.MAX_FRAME_TIME) {
                    delay(1)
                    continue
                }

                startTime = systemTime

                updateGameState(elapsedTime)
            }
        }
    }

    private fun updateGameState(elapsedTime: Float) {
        updateGameObjects()
        checkCollisions()
        updatePositions(elapsedTime)
    }

    private fun updateGameObjects() {
        gameObjects.value = objectsManager.getObjects()
        _gameState.value = GameState(
            Type.GAME,
            drawableManager.mapObjects(gameObjects.value!!),
            emptyList()
        )

        val player = objectsManager.objectStorage.getPlayer()

        if (Physics().doWeNeedToMove(player, screen.maxPlayerHeight)) {
            val offsetY = Physics().moveOffset(player, screen.maxPlayerHeight)

            score.increase(offsetY)
            positionHandler.screenScroll(gameObjects.value!!.filterIsInstance<IMoveable>(), 0f, offsetY)
            objectsManager.updateObjects(offsetY)
        }
    }

    private fun checkCollisions() {
        collisionHandler.checkCollisions(
            objectsManager.objectStorage.getPlayer(),
            screen,
            gameObjects.value!!
        )
    }

    private fun updatePositions(elapsedTime: Float) {
        positionHandler.updatePositions(
            gameObjects.value!!.filterIsInstance<IMoveable>(),
            elapsedTime,
            deltaX
        )
    }

    override fun onShot(startX: Float) {
        objectsManager.createBullet(startX)
    }

    override fun onViewCreated() {
        isGameLoopRunning = true
        gameLoop()
    }

    override fun onPause() {
        sensorHandler.unregister()
        stopGameLoop()
    }

    override fun onResume() {
        sensorHandler.register()
    }
}