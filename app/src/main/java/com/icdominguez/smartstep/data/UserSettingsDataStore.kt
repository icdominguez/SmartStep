package com.icdominguez.smartstep.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.icdominguez.smartstep.domain.UserSettings
import com.icdominguez.smartstep.domain.UserSettingsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserSettingsDataStore(
    private val dataStore: DataStore<Preferences>,
): UserSettings {
    override fun getUserSettingsData(): Flow<UserSettingsData> {
        return dataStore.data.map { preferences ->
            UserSettingsData(
                gender = preferences[GENDER_KEY] ?: "",
                height = preferences[HEIGHT_KEY] ?: 0,
                weight = preferences[WEIGHT_KEY] ?: 0,
                selectedHeightUnit = preferences[SELECTED_HEIGHT_UNIT_KEY] ?: "",
                selectedWeightUnit = preferences[SELECTED_WEIGHT_UNIT_KEY] ?: ""
            )
        }
    }

    override suspend fun setGender(gender: String) {
        dataStore.edit { preferences ->
            preferences[GENDER_KEY] = gender
        }
    }

    override suspend fun setHeightUnit(heightUnit: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_HEIGHT_UNIT_KEY] = heightUnit
        }
    }

    override suspend fun setHeight(height: Int) {
        dataStore.edit { preferences ->
            preferences[HEIGHT_KEY] = height
        }
    }

    override suspend fun setWeight(weight: Int) {
        dataStore.edit { preferences ->
            preferences[WEIGHT_KEY] = weight
        }
    }

    override suspend fun setWeightUnit(weightUnit: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_WEIGHT_UNIT_KEY] = weightUnit
        }
    }

    override fun getStepGoal(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[STEP_GOAL_KEY] ?: 0
        }
    }

    override suspend fun setStepGoal(steps: Int) {
        dataStore.edit { preferences ->
            preferences[STEP_GOAL_KEY] = steps
        }
    }

    companion object {
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val HEIGHT_KEY = intPreferencesKey("height")
        private val WEIGHT_KEY = intPreferencesKey("weight")
        private val SELECTED_HEIGHT_UNIT_KEY = stringPreferencesKey("selected_height_unit")
        private val SELECTED_WEIGHT_UNIT_KEY = stringPreferencesKey("selected_weight_unit")
        private val STEP_GOAL_KEY = intPreferencesKey("step_goal")
    }
}