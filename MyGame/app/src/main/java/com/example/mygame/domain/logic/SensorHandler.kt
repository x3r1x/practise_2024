package com.example.mygame.domain.logic

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener
import com.example.mygame.domain.GameConstants

class SensorHandler(context: Context, private val callback: SensorCallback) : SensorEventListener {

    interface SensorCallback {
        fun onSensorDataChanged(deltaX: Float)
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
        if (event == null || event.sensor.type != Sensor.TYPE_ACCELEROMETER) {
            return
        }

        val deltaX = -event.values[0] * GameConstants.SENSOR_MULTIPLIER
        callback.onSensorDataChanged(deltaX)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}