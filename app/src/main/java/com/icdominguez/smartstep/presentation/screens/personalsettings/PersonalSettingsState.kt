package com.icdominguez.smartstep.presentation.screens.personalsettings

import com.icdominguez.smartstep.domain.model.Gender
import com.icdominguez.smartstep.domain.model.HeightUnit
import com.icdominguez.smartstep.domain.model.WeightUnit

data class PersonalSettingsState(
    val gender: Gender = Gender.MALE,
    // Source of true
    val height: Int = 175,
    val weight: Int = 65,
    val weightUnit: WeightUnit = WeightUnit.KILOS,
    val heightUnit: HeightUnit = HeightUnit.CENTIMETERS,
    // Selected values
    val selectedHeightUnit: HeightUnit = HeightUnit.CENTIMETERS,
    val selectedWeightUnit: WeightUnit = WeightUnit.KILOS,
    val selectedWeightValue: Int = 0,
    val selectedHeightValue: Int = 0,
    val selectedHeightFeet: Int = 0,
    val selectedHeightInches: Int = 0,
    // Display values
    val displayHeight: String = "",
    val displayWeight: String = ""
)
