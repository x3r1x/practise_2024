package com.example.mygame.logic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener

class SensorHandler(context: Context, private val callback: SensorCallback) : SensorEventListener {

    interface SensorCallback {
        fun onSensorDataChanged(deltaX: Float, deltaY: Float)
    }

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    init {
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun register() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

    //Умножать полученные данные на коэфициент какой-то
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val deltaX = -it.values[0] * MULTIPLIER
                val deltaY = it.values[1] * MULTIPLIER
                callback.onSensorDataChanged(deltaX, deltaY)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    companion object {
        const val MULTIPLIER = 3f
    }
}