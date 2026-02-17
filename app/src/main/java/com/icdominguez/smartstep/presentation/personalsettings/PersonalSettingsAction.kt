package com.icdominguez.smartstep.presentation.personalsettings

import com.icdominguez.smartstep.presentation.model.HeightUnit
import com.icdominguez.smartstep.presentation.model.WeightUnit

sealed interface PersonalSettingsAction {
    // region Gender
    data class SetGender(val gender: String) : PersonalSettingsAction
    // end region

    // region Weight
    data class SetWeightUnit(val weightUnit: WeightUnit) : PersonalSettingsAction
    data class OnWeightValueChange(val newWeightValue: Int) : PersonalSettingsAction
    data object OnSetNewWeightValue : PersonalSettingsAction
    data object OnDismissWeightPicker : PersonalSettingsAction
    // end region

    // region Height
    data class SetHeightUnit(val heightUnit: HeightUnit) : PersonalSettingsAction
    data class OnHeightValueChange(val newHeightValue: Int) : PersonalSettingsAction
    data class OnHeightFeetValueChange(val newHeightFeetValue: Int): PersonalSettingsAction
    data class OnHeightInchesValueChange(val newHeightInchesValue: Int): PersonalSettingsAction
    data object OnSetNewHeightValue : PersonalSettingsAction
    data object OnDismissHeightPicker : PersonalSettingsAction
    // end region
}