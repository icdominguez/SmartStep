package com.icdominguez.smartstep.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.smartstep.domain.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userSettings: UserSettings
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event = Channel<HomeEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userSettings.getBatteryOptIgnored().collect { isBatteryOptIgnored ->
                _state.value = _state.value.copy(
                    isBatteryOptIgnored = isBatteryOptIgnored
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            userSettings.getStepGoal().collect { stepGoal ->
                _state.value = _state.value.copy(
                    stepGoal = stepGoal,
                    selectedStepGoal = stepGoal,
                )
            }
        }
    }

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnActivityRecognitionChecked -> onActivityRecognitionPermissionChecked(action.isGranted)
            is HomeAction.OnActivityRecognitionGranted -> onActivityRecognitionGranted()
            is HomeAction.OnActivityRecognitionDenied -> onActivityRecognitionDenied(action.showRationale)
            is HomeAction.OnActivityRecognitionRequest -> onActivityRecognitionRequest()

            is HomeAction.OnOpenManualSettings -> onOpenManualSettings()

            is HomeAction.OnIgnoreBatteryOptimizationsResponse -> onIgnoreBatteryOptimizationResponse(action.isIgnored)

            is HomeAction.OnShowStepGoalDialog -> onShowStepGoalDialog()
            is HomeAction.OnStepGoalChange -> onStepGoalChange(action.stepGoal)
            is HomeAction.OnStepGoalConfirm -> onStepGoalConfirm()
            is HomeAction.OnStepGoalDismiss -> onStepGoalDismiss()

            is HomeAction.OnContinueBackgroundAccess -> onContinueBackgroundAccess()

            is HomeAction.OnExitClick -> onExitClick()
            is HomeAction.OnExitConfirm -> onExitConfirm()
            is HomeAction.OnExitDismiss -> onExitDismiss()
        }
    }

    private fun onActivityRecognitionPermissionChecked(isGranted: Boolean) {
        viewModelScope.launch {
            if (isGranted) {
                if (state.value.isBatteryOptIgnored == null) {
                    _state.value = _state.value.copy(
                        showBatteryRecommendedDialog = true
                    )
                }
            } else {
                _event.send(HomeEvent.RequestActivityRecognition)
            }
        }
    }

    private fun onActivityRecognitionGranted() {
        _state.value = _state.value.copy(
            showBatteryRecommendedDialog = true
        )
    }

    private fun onActivityRecognitionDenied(shouldShowRationale: Boolean) {
        if(shouldShowRationale) {
            _state.value = _state.value.copy(
                showPermissionExplanationSheet = true
            )
        } else {
            _state.value = _state.value.copy(
                showManualPermissionSheet = true
            )
        }
    }

    private fun onActivityRecognitionRequest() {
        _state.value = _state.value.copy(
            showPermissionExplanationSheet = false
        )

        viewModelScope.launch {
            _event.send(HomeEvent.RequestActivityRecognition)
        }
    }

    private fun onOpenManualSettings() {
        _state.value = _state.value.copy(
            showManualPermissionSheet = false
        )

        viewModelScope.launch {
            _event.send(HomeEvent.RequestOpenAppSettings)
        }
    }

    private fun onIgnoreBatteryOptimizationResponse(isIgnored: Boolean) {
        viewModelScope.launch {
            userSettings.setBatteryOptIgnored(isIgnored)
        }
    }

    private fun onStepGoalDismiss() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                showStepGoalModalBottomSheet = false,
                selectedStepGoal = state.value.stepGoal
            )
        }
    }

    private fun onStepGoalConfirm() {
        viewModelScope.launch {
            userSettings.setStepGoal(state.value.selectedStepGoal)
            _state.value = _state.value.copy(
                showStepGoalModalBottomSheet = false,
            )
        }
    }

    private fun onStepGoalChange(stepGoal: Int) {
        _state.value = _state.value.copy(
            selectedStepGoal = stepGoal
        )
    }

    private fun onShowStepGoalDialog() {
        _state.value = _state.value.copy(
            showStepGoalModalBottomSheet = true
        )
    }

    private fun onContinueBackgroundAccess() {
        _state.value = _state.value.copy(
            showBatteryRecommendedDialog = false
        )

        viewModelScope.launch {
            _event.send(HomeEvent.RequestIgnoreBatteryOptimizations)
        }
    }

    private fun onExitClick() {
        _state.value = _state.value.copy(
            showExitDialog = true
        )
    }

    private fun onExitConfirm() {
        _state.value = _state.value.copy(
            showExitDialog = false
        )
    }

    private fun onExitDismiss() {
        _state.value = _state.value.copy(
            showExitDialog = false
        )
    }
}