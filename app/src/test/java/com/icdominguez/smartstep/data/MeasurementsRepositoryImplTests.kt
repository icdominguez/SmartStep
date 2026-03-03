package com.icdominguez.smartstep.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MeasurementsRepositoryImplTests {
    private val repository = MeasurementRepositoryImpl()

    @Test
    fun `cmToFeetAndInches converts 180cm properly`() {
        val result = repository.cmToFeetAndInches(180)
        assertEquals(5, result.first)
        assertEquals(11, result.second)
    }

    @Test
    fun `cmToFeetAndInches converts 0cm returns 0`() {
        val result = repository.cmToFeetAndInches(0)
        assertEquals(0, result.first)
        assertEquals(0, result.second)
    }

    @Test
    fun `cmToFeetAndInches rounds inches correctly`() {
        val result = repository.cmToFeetAndInches(30)
        assertEquals(0, result.first)
        assertEquals(12, result.second)
    }

    @Test
    fun `feetAndInchesToCm converts 5 feet 11 inches properly`() {
        val result = repository.feetAndInchesToCm(5, 11)
        assertEquals(180, result)
    }

    @Test
    fun `feetAndInchesToCm converts 0 feet 0 inches return zero`() {
        val result = repository.feetAndInchesToCm(0, 0)
        assertEquals(0, result)
    }

    @Test
    fun `kgToLbs converts 70kg properly`() {
        val result = repository.kgToLbs(70)
        assertEquals(154, result)
    }

    @Test
    fun `kgToLbs converts 0kg returns zero`() {
        val result = repository.kgToLbs(0)
        assertEquals(0, result)
    }

    @Test
    fun `LbsToKg converts 154lbs properly`() {
        val result = repository.lbsToKg(154)
        assertEquals(70, result)
    }

    @Test
    fun `lbsToKg converts 0lbs returns zero`() {
        val result = repository.lbsToKg(0)
        assertEquals(0, result)
    }
}