package com.icdominguez.smartstep.fakes

import com.icdominguez.smartstep.domain.UserSettings
import com.icdominguez.smartstep.domain.UserSettingsData
import com.icdominguez.smartstep.domain.UserSettingsDefaults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class UserSettingsFake : UserSettings {
    private val userSettingsDataFlow = MutableStateFlow(
        UserSettingsData(
            gender = UserSettingsDefaults.GENDER_DEFAULT,
            height = UserSettingsDefaults.HEIGHT_DEFAULT,
            weight = UserSettingsDefaults.WEIGHT_DEFAULT,
            selectedHeightUnit = UserSettingsDefaults.HEIGHT_UNIT_DEFAULT,
            selectedWeightUnit = UserSettingsDefaults.WEIGHT_UNIT_DEFAULT
        )
    )
    private val stepGoalFlow = MutableStateFlow(UserSettingsDefaults.STEP_DEFAULT)
    private val backgroundAccessFlow = MutableStateFlow<Boolean?>(null)

    override fun getUserSettingsData(): Flow<UserSettingsData> {
        return userSettingsDataFlow
    }

    override suspend fun setGender(gender: String) {
        userSettingsDataFlow.value = userSettingsDataFlow.value.copy(
            gender = gender
        )
    }

    override suspend fun setHeightUnit(heightUnit: String) {
        userSettingsDataFlow.value = userSettingsDataFlow.value.copy(
            selectedHeightUnit = heightUnit
        )
    }

    override suspend fun setHeight(height: Int) {
        userSettingsDataFlow.value = userSettingsDataFlow.value.copy(
            height = height
        )
    }

    override suspend fun setWeight(weight: Int) {
        userSettingsDataFlow.value = userSettingsDataFlow.value.copy(
            weight = weight
        )
    }

    override suspend fun setWeightUnit(weightUnit: String) {
        userSettingsDataFlow.value = userSettingsDataFlow.value.copy(
            selectedWeightUnit = weightUnit
        )
    }

    override fun getBackgroundAccessEnabled(): Flow<Boolean?> =
        backgroundAccessFlow

    override suspend fun setBackgroundAccessEnabled(ignored: Boolean) {
        backgroundAccessFlow.value = ignored
    }

    override fun getStepGoal(): Flow<Int> =
        stepGoalFlow

    override suspend fun setStepGoal(steps: Int) {
        stepGoalFlow.value = steps
    }

}