package com.icdominguez.smartstep.presentation.personalsettings

import com.icdominguez.smartstep.presentation.model.HeightUnit
import com.icdominguez.smartstep.presentation.model.WeightUnit

data class PersonalSettingsState(
    val gender: String = "",
    val height: Int = 175,
    val weight: Int = 65,
    val weightUnit: WeightUnit = WeightUnit.KILOS,
    val heightUnit: HeightUnit = HeightUnit.CENTIMETERS,
    val selectedWeightValue: Int = 0,
    val selectedHeightValue: Int = 0,
    val selectedHeightFeet: Int = 0,
    val selectedHeightInches: Int = 0,
    val selectedHeightUnit: HeightUnit = HeightUnit.CENTIMETERS,
    val selectedWeightUnit: WeightUnit = WeightUnit.KILOS,
)
