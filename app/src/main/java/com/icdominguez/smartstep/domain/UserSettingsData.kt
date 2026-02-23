package com.icdominguez.smartstep.domain

data class UserSettingsData(
    val gender: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val selectedHeightUnit: String = "",
    val selectedWeightUnit: String = ""
)
