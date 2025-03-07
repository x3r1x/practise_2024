package com.example.mygame.domain.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.UI.GameSoundsPlayer
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


class SingleplayerGameplay(
    private val objectsManager: ObjectsManager,
    private val sensorHandler: SensorHandler,
    private val positionHandler: PositionHandler,
    private val collisionHandler: CollisionHandler,
    private val drawableManager: DrawableManager,
    private val screen: Screen,
    private val audioPlayer: GameSoundsPlayer
) : IGameplay {
    private var isGameLoopRunning = false

    private var shotCooldown = 0f

    private var deltaX = 0f
    override val score = Score()

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override var id = 0
    override var winnerId = 0

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState
    private val gameObjects = MutableLiveData<List<IGameObject>>()

    private val _scoreObservable = MutableLiveData<Int>()
    override val scoreObservable: LiveData<Int> = _scoreObservable

    override fun startGameLoop() {
        isGameLoopRunning = true
        gameLoop()
    }

    override fun stopGameLoop() {
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
        updateGameObjects(elapsedTime)
        checkCollisions()
        updatePositions(elapsedTime)

        if (shotCooldown > 0f) {
            shotCooldown -= elapsedTime
        }
    }

    private fun updateGameObjects(elapsedTime: Float) {
        gameObjects.value = objectsManager.getObjects()
        _gameState.value = GameState(
            Type.GAME,
            drawableManager.mapObjects(gameObjects.value!!),
            emptyList()
        )

        val player = objectsManager.objectStorage.getPlayer()
        player.bonuses.updateBonuses(elapsedTime, audioPlayer)
        drawableManager.playerViewFactory.selectedBonusViewFactory.updateBonuses(elapsedTime)

        if (Physics().doWeNeedToMove(player, screen.maxPlayerHeight)) {
            val offsetY = Physics().moveOffset(player, screen.maxPlayerHeight)

            score.increase(offsetY)
            objectsManager.levelGenerator.currentScore = score.getScore()
            positionHandler.screenScroll(gameObjects.value!!.filterIsInstance<IMoveable>(), 0f, offsetY)
            objectsManager.updateObjects(offsetY)
        }
    }

    private fun checkCollisions() {
        collisionHandler.checkCollisions(
            objectsManager.objectStorage.getPlayer(),
            screen,
            gameObjects.value!!,
            audioPlayer
        )
    }

    private fun updatePositions(elapsedTime: Float) {
        positionHandler.updatePositions(
            gameObjects.value!!.filterIsInstance<IMoveable>(),
            elapsedTime,
            deltaX
        )
    }

    override fun onSensorDataChanged(deltaX: Float) {
        this.deltaX = deltaX
    }

    override fun onShot(startX: Float) {
        if (shotCooldown <= 0f) {
            audioPlayer.playShootSound()

            objectsManager.createBullet(startX)
            shotCooldown = GameConstants.SHOT_COOLDOWN
        }
    }

    override fun onPause() {
        sensorHandler.unregister()
        stopGameLoop()
    }

    override fun onResume() {
        sensorHandler.register()
    }

    override fun onDestroy() {
    }

    override fun sendReadyMessage() {
        TODO("Not yet implemented")
    }
}