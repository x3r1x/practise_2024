import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygame.CollisionHandler
import com.example.mygame.SensorHandler
import com.example.mygame.`interface`.Drawable
import com.example.mygame.`object`.Ball
import com.example.mygame.`object`.Platform
import kotlinx.coroutines.*

class GameViewModel(application: Application) : AndroidViewModel(application), SensorHandler.SensorCallback {
    private val _gameObjects = MutableLiveData<List<Drawable>>()
    val gameObjects: LiveData<List<Drawable>> get() = _gameObjects

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val ball = Ball().apply { setPosition(500f, 500f) }
    private val platform1 = Platform().apply { setPosition(100f, 950f) }
    private val platform2 = Platform().apply { setPosition(600f, 1150f) }
    private val platform3 = Platform().apply { setPosition(200f, 1650f) }
    private val platforms = listOf(platform1, platform2, platform3)

    private lateinit var collisionHandler: CollisionHandler
    private lateinit var sensorHandler: SensorHandler

    private var deltaX = 0f
    private var deltaY = 0f

    fun initialize(screenWidth: Float, screenHeight: Float) {
        collisionHandler = CollisionHandler(screenWidth, screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        startGameLoop()
    }

    fun startGameLoop() {
        val frameRate = 1000 / 60 //~16ms = 60 FPS
        uiScope.launch {
            while (true) {
                val startTime = System.currentTimeMillis()

                updateGame()

                println("Frame at: $startTime")
                countFrame()

                val elapsedTime = System.currentTimeMillis() - startTime
                val delayTime = frameRate - elapsedTime
                if(delayTime > 0) {
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


    private fun updateGame() {
        collisionHandler.checkCollisions(ball, platforms)
        ball.updatePosition(deltaX, deltaY)
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