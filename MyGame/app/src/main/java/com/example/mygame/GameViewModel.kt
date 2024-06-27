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
    lateinit var sensorHandler: SensorHandler

    var deltaX = 0f
    var deltaY = 0f

    fun initialize(screenWidth: Float, screenHeight: Float) {
        collisionHandler = CollisionHandler(screenWidth, screenHeight)
        sensorHandler = SensorHandler(getApplication(), this)
        startGameLoop()
    }

    private fun startGameLoop() {
        uiScope.launch {
            while (true) {
                delay(16) // 60 FPS
                updateGame()
            }
        }
    }

    fun updateDelta(deltaX: Float, deltaY: Float) {
        this.deltaX = deltaX
        this.deltaY = deltaY
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
}
