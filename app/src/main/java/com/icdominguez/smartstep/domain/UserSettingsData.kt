package com.icdominguez.smartstep.domain

data class UserSettingsData(
    val gender: String,
    val height: Int,
    val weight: Int,
    val selectedHeightUnit: String,
    val selectedWeightUnit: String ,
)
