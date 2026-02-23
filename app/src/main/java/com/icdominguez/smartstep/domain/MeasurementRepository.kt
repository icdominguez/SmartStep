package com.icdominguez.smartstep.domain

interface MeasurementRepository {
    fun cmToFeetAndInches(cm: Int): Pair<Int, Int>
    fun feetAndInchesToCm(feet: Int, inches: Int): Int
    fun kgToLbs(kg: Int): Int
    fun lbsToKg(lbs: Int): Int
}