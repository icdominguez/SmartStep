package com.icdominguez.smartstep.domain

import com.icdominguez.smartstep.domain.model.WeightUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WeightEnumTest {
    @Test
    fun `fromLabel return KILOS when label is kg`() {
        assertEquals(WeightUnit.KILOS, WeightUnit.fromLabel("kg"))
    }

    @Test
    fun `fromLabel return POUNDS when label is lbs`() {
        assertEquals(WeightUnit.POUNDS, WeightUnit.fromLabel("lbs"))
    }

    @Test
    fun `fromLabel return KILOS as default value when label is unknown`() {
        assertEquals(WeightUnit.KILOS, WeightUnit.fromLabel("unknown"))
    }
}