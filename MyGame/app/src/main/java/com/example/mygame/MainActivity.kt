package com.example.mygame

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View

class MainActivity : Activity(), SensorHandler.SensorCallback {
    private lateinit var ballView: BallView
    private lateinit var sensorHandler: SensorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ballView = BallView(this)
        setContentView(ballView)

        sensorHandler = SensorHandler(this, this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        sensorHandler.unregister()
    }

    override fun onSensorDataChanged(deltaX: Float, deltaY: Float) {
        ballView.updateBallPosition(deltaX)
    }
}


