package com.icdominguez.smartstep.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StepCounterManager(
    context: Context
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private val _steps = MutableStateFlow(0)
    val steps: StateFlow<Int> = _steps

    private var baseline: Int? = null
    private var isRegistered = false

    fun start() {
        Log.d("icd", "Step counter initialized")

        val sensor = stepCounterSensor ?: return

        baseline = null
        _steps.value = 0

        if(!isRegistered) {
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI
            )
            isRegistered = true
        }
    }

    fun stop() {
        if (isRegistered) {
            Log.d("icd", "Step counter stopped")
            sensorManager.unregisterListener(this)
            isRegistered = false
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        val totalSinceBoot = event?.values[0]?.toInt() ?: 0
        val base = baseline ?: totalSinceBoot.also { baseline = it }

        val steps = (totalSinceBoot - base).coerceAtLeast(0)
        Log.d("icd", "Step sensor changed: STEPS - $steps")
        _steps.value = (totalSinceBoot - base).coerceAtLeast(0)
    }
}