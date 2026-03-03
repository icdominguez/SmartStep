package com.icdominguez.smartstep.domain

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.icdominguez.smartstep.data.StepCounterManager
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StepCounterManagerTest {
    private lateinit var manager: StepCounterManager

    private val sensorManager: SensorManager = mockk(relaxed = true)
    private val stepCounterSensor: Sensor = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        manager = StepCounterManager(sensorManager, stepCounterSensor)
    }

    @Test
    fun `start registers listener`() {
        // Act
        manager.start()
        // Arrange
        verify { sensorManager.registerListener(manager, stepCounterSensor, SensorManager.SENSOR_DELAY_UI )}
    }
    
    @Test
    fun `stop unregisters listener`() {
        // Act
        manager.start()
        manager.stop()
        
        // Assert
        verify { sensorManager.unregisterListener(manager) }
    }

    @Test
    fun `start does not register twice`() {
        // Act
        manager.start()
        manager.start()

        // Assert
        verify(exactly = 1) { sensorManager.registerListener(manager, stepCounterSensor, SensorManager.SENSOR_DELAY_UI) }
    }

    @Test
    fun `steps are calculated relative to baseline`() = runTest {
        manager.start()

        manager.simulateSensorEvent(100f)
        assertEquals(0, manager.steps.value)

        manager.simulateSensorEvent(110f) // 110 - 100 = 10
        assertEquals(10, manager.steps.value)

        manager.simulateSensorEvent(115f) // 115 - 100 = 15
        assertEquals(15, manager.steps.value)
    }

    @Test
    fun `start resets baseline and steps`() = runTest {
        manager.start()
        manager.simulateSensorEvent(100f)
        manager.simulateSensorEvent(150f)

        manager.stop()
        manager.start()

        assertEquals(0, manager.steps.value)
    }

    @Test
    fun `no sensor does nothing`() {
        val managerWithoutSensor = StepCounterManager(sensorManager, null)
        managerWithoutSensor.start()
        verify(exactly = 0) { sensorManager.registerListener(managerWithoutSensor, stepCounterSensor, SensorManager.SENSOR_DELAY_UI) }
    }
}

private fun StepCounterManager.simulateSensorEvent(value: Float) {
    val event = mockk<SensorEvent>(relaxed = true)
    val valuesField = SensorEvent::class.java.getDeclaredField("values")
    valuesField.isAccessible = true
    valuesField.set(event, floatArrayOf(value))

    this.onSensorChanged(event)
}