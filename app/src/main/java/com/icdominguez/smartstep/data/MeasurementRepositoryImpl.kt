package com.icdominguez.smartstep.data

import com.icdominguez.smartstep.domain.MeasurementRepository
import kotlin.math.roundToInt

class MeasurementRepositoryImpl : MeasurementRepository {
    override fun cmToFeetAndInches(cm: Int): Pair<Int, Int> {
        val totalInches = cm / 2.54
        val feet = (totalInches / 12).toInt()
        val inches = (totalInches % 12).roundToInt()
        return Pair(feet, inches)
    }

    override fun feetAndInchesToCm(feet: Int, inches: Int): Int {
        return ((feet * 12 + inches) * 2.54).roundToInt()
    }

    override fun kgToLbs(kg: Int): Int =
        (kg * 2.20462).roundToInt()

    override fun lbsToKg(lbs: Int): Int =
        (lbs / 2.20462).roundToInt()

}