package com.icdominguez.smartstep.presentation.screens.personalsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.smartstep.domain.MeasurementRepository
import com.icdominguez.smartstep.domain.UserSettings
import com.icdominguez.smartstep.presentation.model.Gender
import com.icdominguez.smartstep.presentation.model.HeightUnit
import com.icdominguez.smartstep.presentation.model.WeightUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PersonalSettingsViewModel(
    private val userSettings: UserSettings,
    private val measurementRepository: MeasurementRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PersonalSettingsState())
    val state = _state.asStateFlow()

    private val _event = Channel<PersonalSettingsEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userSettings = userSettings
                .getUserSettingsData()
                .first()

            val gender = userSettings.gender.ifEmpty { Gender.FEMALE.name }

            val height = if (userSettings.height == 0) 170 else userSettings.height
            val weight = if (userSettings.weight == 0) 65 else userSettings.weight

            val heightUnit = HeightUnit.fromLabel(userSettings.selectedHeightUnit)
            val weightUnit = WeightUnit.fromLabel(userSettings.selectedWeightUnit)

            val (feet, inches) =
                measurementRepository.cmToFeetAndInches(height)

            val kgToLibs =
                measurementRepository.kgToLbs(weight)

            val displayHeight = when (heightUnit) {
                HeightUnit.CENTIMETERS -> "$height cm"
                HeightUnit.FEET -> "${feet}ft/${inches}in"
            }

            val displayWeight = when(weightUnit) {
                WeightUnit.KILOS -> "$weight ${WeightUnit.KILOS.label}"
                WeightUnit.POUNDS -> {
                    val kgToLbs = measurementRepository.kgToLbs(weight)
                    "$kgToLbs ${WeightUnit.POUNDS.label}"
                }
            }

            _state.value = _state.value.copy(
                gender = Gender.valueOf(gender),
                heightUnit = heightUnit,
                selectedHeightUnit = heightUnit,
                weightUnit = weightUnit,
                selectedWeightUnit = weightUnit,
                height = height,
                selectedHeightValue = height,
                selectedHeightFeet = feet,
                selectedHeightInches = inches,
                weight = if (weightUnit == WeightUnit.KILOS) weight else kgToLibs,
                selectedWeightValue = if (weightUnit == WeightUnit.KILOS) userSettings.weight else kgToLibs,
                displayHeight = displayHeight,
                displayWeight = displayWeight,
            )
        }
    }

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
            is PersonalSettingsAction.OnStartButtonClicked -> onSavePersonalSettingsClick()
            is PersonalSettingsAction.OnSkipButtonClicked -> onSavePersonalSettingsClick()
        }
    }

    private fun setGender(gender: Gender) {
        _state.value = _state.value.copy(
            gender = gender
        )
    }

    private fun setWeightUnit(weightUnit: WeightUnit) {
        when (weightUnit) {
            WeightUnit.KILOS -> {
                val lbsToKg = measurementRepository.lbsToKg(state.value.selectedWeightValue)
                _state.value = _state.value.copy(
                    selectedWeightUnit = weightUnit,
                    selectedWeightValue = lbsToKg
                )
            }

            WeightUnit.POUNDS -> {
                val kgToLbs =  measurementRepository.kgToLbs(state.value.selectedWeightValue)
                _state.value = _state.value.copy(
                    selectedWeightUnit = weightUnit,
                    selectedWeightValue = kgToLbs
                )
            }
        }
    }

    private fun setHeightUnit(heightUnit: HeightUnit) {
        when (heightUnit) {
            HeightUnit.CENTIMETERS -> {
                val centimeters = measurementRepository.feetAndInchesToCm(
                    state.value.selectedHeightFeet,
                    state.value.selectedHeightInches
                )

                _state.value = _state.value.copy(
                    selectedHeightUnit = heightUnit,
                    selectedHeightValue = centimeters,
                )
            }

            HeightUnit.FEET -> {
                val (feet, inches) = measurementRepository.cmToFeetAndInches(state.value.selectedHeightValue)

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
        val displayWeight = "${state.value.selectedWeightValue} ${state.value.selectedWeightUnit.label}"

        _state.value = state.value.copy(
            weightUnit = state.value.selectedWeightUnit,
            weight = state.value.selectedWeightValue,
            displayWeight = displayWeight
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

        val displayHeight = when (state.value.selectedHeightUnit) {
            HeightUnit.CENTIMETERS -> "${state.value.selectedHeightValue} cm"
            HeightUnit.FEET -> "${state.value.selectedHeightFeet}ft/${state.value.selectedHeightInches}in"
        }

        _state.value = state.value.copy(
            displayHeight = displayHeight,
        )
    }

    private fun onDismissHeightPicker() {
        val (feet, inches) = measurementRepository.cmToFeetAndInches(state.value.height)

        _state.value = _state.value.copy(
            selectedHeightUnit = state.value.heightUnit,
            selectedHeightValue = state.value.height,
            selectedHeightFeet = feet,
            selectedHeightInches = inches
        )
    }

    private fun onSavePersonalSettingsClick() {
        viewModelScope.launch(Dispatchers.IO) {
            userSettings.setGender(state.value.gender.name)
            val height = if(state.value.selectedHeightUnit == HeightUnit.FEET) {
                measurementRepository.feetAndInchesToCm(
                    state.value.selectedHeightFeet,
                    state.value.selectedHeightInches
                )
            } else {
                state.value.selectedHeightValue
            }
            userSettings.setHeight(height)
            userSettings.setHeightUnit(state.value.selectedHeightUnit.label)
            userSettings.setWeightUnit(state.value.selectedWeightUnit.label)
            val weight = if(state.value.selectedWeightUnit == WeightUnit.POUNDS) {
                measurementRepository.lbsToKg(state.value.selectedWeightValue)
            } else {
                state.value.selectedWeightValue
            }
            userSettings.setWeight(weight)

            _event.send(PersonalSettingsEvent.OnPersonalSettingsSaved)
        }
    }
}