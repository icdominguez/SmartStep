package com.icdominguez.smartstep.presentation.personalsettings

import androidx.lifecycle.ViewModel
import com.icdominguez.smartstep.presentation.model.HeightUnit
import com.icdominguez.smartstep.presentation.model.WeightUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

class PersonalSettingsViewModel : ViewModel() {
    private val _state = MutableStateFlow(PersonalSettingsState())
    val state = _state.asStateFlow()

    fun onAction(action: PersonalSettingsAction) {
        when (action) {
            is PersonalSettingsAction.SetGender -> setGender(action.gender)
            is PersonalSettingsAction.SetWeightUnit -> setWeightUnit(action.weightUnit)
            is PersonalSettingsAction.SetHeightUnit -> setHeightUnit(action.heightUnit)
            is PersonalSettingsAction.OnWeightValueChange -> onWeightValueChange(action.newWeightValue)
            is PersonalSettingsAction.OnSetNewWeightValue -> onSetNewWeightValue()
            is PersonalSettingsAction.OnDismissWeightPicker -> onDismissWeightPicker()
            is PersonalSettingsAction.OnHeightValueChange -> onHeightValueChange(action.newHeightValue)
            is PersonalSettingsAction.OnHeightFeetValueChange -> onHeightFeetValueChange(action.newHeightFeetValue)
            is PersonalSettingsAction.OnHeightInchesValueChange -> onHeightInchesValueChange(action.newHeightInchesValue)
            is PersonalSettingsAction.OnSetNewHeightValue -> onSetNewHeightValue()
            is PersonalSettingsAction.OnDismissHeightPicker -> onDismissHeightPicker()
        }
    }

    private fun setGender(gender: String) {
        _state.value = _state.value.copy(
            gender = gender
        )
    }

    private fun setWeightUnit(weightUnit: WeightUnit) {
        when (weightUnit) {
            WeightUnit.KILOS -> {
                val poundsToKilos = (state.value.selectedWeightValue / 2.20462).roundToInt()
                _state.value = _state.value.copy(
                    selectedWeightUnit = weightUnit,
                    selectedWeightValue = poundsToKilos
                )
            }

            WeightUnit.POUNDS -> {
                val kilosToPounds = (state.value.selectedWeightValue * 2.20462).roundToInt()
                _state.value = _state.value.copy(
                    selectedWeightUnit = weightUnit,
                    selectedWeightValue = kilosToPounds
                )
            }
        }
    }

    private fun setHeightUnit(heightUnit: HeightUnit) {
        when (heightUnit) {
            HeightUnit.CENTIMETERS -> {
                val centimeters = feetInchesToCentimeters(
                    state.value.selectedHeightFeet,
                    state.value.selectedHeightInches
                )

                _state.value = _state.value.copy(
                    selectedHeightUnit = heightUnit,
                    selectedHeightValue = centimeters,
                )
            }

            HeightUnit.FEET -> {
                val (feet, inches) = centimetersToFeetAndInches(state.value.selectedHeightValue)

                _state.value = _state.value.copy(
                    selectedHeightUnit = heightUnit,
                    selectedHeightFeet = feet,
                    selectedHeightInches = inches
                )
            }
        }
    }

    private fun onWeightValueChange(weight: Int) {
        _state.value = _state.value.copy(
            selectedWeightValue = weight
        )
    }

    private fun onSetNewWeightValue() {
        _state.value = _state.value.copy(
            weightUnit = state.value.selectedWeightUnit,
            weight = state.value.selectedWeightValue
        )
    }

    private fun onDismissWeightPicker() {
        _state.value = _state.value.copy(
            selectedWeightUnit = state.value.weightUnit,
            selectedWeightValue = state.value.weight
        )
    }

    private fun onHeightValueChange(newHeightValue: Int) {
        _state.value = _state.value.copy(
            selectedHeightValue = newHeightValue
        )
    }

    private fun onHeightFeetValueChange(newHeightFeetValue: Int) {
        _state.value = _state.value.copy(
            selectedHeightFeet = newHeightFeetValue
        )
    }

    private fun onHeightInchesValueChange(newHeightInchesValue: Int) {
        _state.value = _state.value.copy(
            selectedHeightInches = newHeightInchesValue
        )
    }

    private fun onSetNewHeightValue() {
        _state.value = _state.value.copy(
            heightUnit = state.value.selectedHeightUnit,
            height = state.value.selectedHeightValue
        )
    }

    private fun onDismissHeightPicker() {
        val (feet, inches) = centimetersToFeetAndInches(state.value.height)

        _state.value = _state.value.copy(
            selectedHeightUnit = state.value.heightUnit,
            selectedHeightValue = state.value.height,
            selectedHeightFeet = feet,
            selectedHeightInches = inches
        )
    }

    private fun feetInchesToCentimeters(feet: Int, inches: Int): Int {
        val totalInches = feet * 12 + inches
        val centimeters = totalInches * 2.54
        return centimeters.roundToInt()
    }

    private fun centimetersToFeetAndInches(centimeters: Int): Pair<Int, Int> {
        val totalInches = centimeters / 2.54
        val feet = floor(totalInches / 12).roundToInt()
        val inches = round(totalInches - feet * 12).roundToInt()
            .coerceIn(0, 11)
        return feet to inches
    }
}