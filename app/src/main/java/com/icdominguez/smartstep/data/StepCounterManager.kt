package com.icdominguez.smartstep.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.icdominguez.smartstep.domain.StepCounter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class StepCounterManager(
    private val sensorManager: SensorManager,
    private val stepCounterSensor: Sensor?
) : StepCounter, SensorEventListener {

    private val _steps = MutableStateFlow(0)
    override val steps: StateFlow<Int> = _steps

    private var baseline: Int? = null
    private var isRegistered = false

    override fun start() {
        Timber.tag("icd").d("Step counter initialized")

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

    override fun stop() {
        if (isRegistered) {
            Timber.tag("icd").d("Step counter stopped")
            sensorManager.unregisterListener(this)
            isRegistered = false
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        val totalSinceBoot = event?.values[0]?.toInt() ?: 0
        val base = baseline ?: totalSinceBoot.also { baseline = it }

        val steps = (totalSinceBoot - base).coerceAtLeast(0)
        Timber.tag("icd").d("Step sensor changed: STEPS - $steps")
        _steps.value = (totalSinceBoot - base).coerceAtLeast(0)
    }
}