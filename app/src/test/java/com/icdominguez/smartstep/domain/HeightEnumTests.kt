package com.icdominguez.smartstep.domain

import com.icdominguez.smartstep.domain.model.HeightUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HeightEnumTests {
    @Test
    fun `fromLabel return FEET when label is ft-in`() {
        assertEquals(HeightUnit.FEET, HeightUnit.fromLabel("ft/in"))
    }

    @Test
    fun `fromLabel return CENTIMETERS when label is cm`() {
        assertEquals(HeightUnit.CENTIMETERS, HeightUnit.fromLabel("cm"))
    }

    @Test
    fun `fromLabel return CENTIMETERS as default value when label is unknown`() {
        assertEquals(HeightUnit.CENTIMETERS, HeightUnit.fromLabel("unknown"))
    }
}