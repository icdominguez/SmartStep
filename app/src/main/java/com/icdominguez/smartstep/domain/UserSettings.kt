package com.icdominguez.smartstep.domain

import kotlinx.coroutines.flow.Flow

interface UserSettings {
    fun getUserSettingsData(): Flow<UserSettingsData>

    suspend fun setGender(gender: String)
    suspend fun setHeightUnit(heightUnit: String)
    suspend fun setHeight(height: Int)
    suspend fun setWeight(weight: Int)
    suspend fun setWeightUnit(weightUnit: String)

    fun getBackgroundAccessEnabled(): Flow<Boolean?>
    suspend fun setBackgroundAccessEnabled(ignored: Boolean)

    fun getStepGoal(): Flow<Int>
    suspend fun setStepGoal(steps: Int)
}