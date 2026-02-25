package com.icdominguez.smartstep.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.smartstep.data.StepCounterManager
import com.icdominguez.smartstep.domain.UserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userSettings: UserSettings,
    private val stepCounterManager: StepCounterManager,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event = Channel<HomeEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userSettings.getBackgroundAccessEnabled().collect { isBackgroundAccessEnabled ->
                if(isBackgroundAccessEnabled == true) stepCounterManager.start()
                _state.value = _state.value.copy(
                    isBackgroundAccessEnabled = isBackgroundAccessEnabled
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

        viewModelScope.launch {
            stepCounterManager.steps.collect { steps ->
                _state.value = _state.value.copy(
                    steps = steps
                )
            }
        }
    }

    fun onAction(action: HomeAction) {
        when(action) {
            // region Permissions
            is HomeAction.OnActivityRecognitionChecked -> onActivityRecognitionPermissionChecked(action.isGranted)
            is HomeAction.OnActivityRecognitionGranted -> onActivityRecognitionGranted()
            is HomeAction.OnActivityRecognitionDenied -> onActivityRecognitionDenied(action.showRationale)
            is HomeAction.OnActivityRecognitionRequest -> onActivityRecognitionRequest()
            is HomeAction.OnOpenManualSettings -> onOpenManualSettings()
            is HomeAction.OnBackgroundAccessPermissionResponse -> onBackgroundAccessPermissionResponse(action.isEnabled)
            is HomeAction.OnContinueBackgroundAccess -> onContinueBackgroundAccess()
            // end region
            // region StepGoal
            is HomeAction.OnShowStepGoalDialog -> onShowStepGoalDialog()
            is HomeAction.OnStepGoalChange -> onStepGoalChange(action.stepGoal)
            is HomeAction.OnStepGoalConfirm -> onStepGoalConfirm()
            is HomeAction.OnStepGoalDismiss -> onStepGoalDismiss()
            // end region
            // region Exit
            is HomeAction.OnExitClick -> onExitClick()
            is HomeAction.OnExitConfirm -> onExitConfirm()
            is HomeAction.OnExitDismiss -> onExitDismiss()
            // end region
        }
    }

    private fun onActivityRecognitionPermissionChecked(isGranted: Boolean) {
        viewModelScope.launch {
            if (isGranted) {
                if (state.value.isBackgroundAccessEnabled == null) {
                    _state.value = _state.value.copy(
                        showBackgroundAccessRecommendedDialog = true
                    )
                }
            } else {
                _event.send(HomeEvent.RequestActivityRecognition)
            }
        }
    }

    private fun onActivityRecognitionGranted() {
        _state.value = _state.value.copy(
            showBackgroundAccessRecommendedDialog = true
        )
    }

    private fun onActivityRecognitionDenied(shouldShowRationale: Boolean) {
        if(shouldShowRationale) {
            _state.value = _state.value.copy(
                showMotionSensorsDialog = true
            )
        } else {
            _state.value = _state.value.copy(
                showEnableAccessManuallyDialog = true
            )
        }
    }

    private fun onActivityRecognitionRequest() {
        _state.value = _state.value.copy(
            showMotionSensorsDialog = false
        )

        viewModelScope.launch {
            _event.send(HomeEvent.RequestActivityRecognition)
        }
    }

    private fun onOpenManualSettings() {
        _state.value = _state.value.copy(
            showEnableAccessManuallyDialog = false
        )

        viewModelScope.launch {
            _event.send(HomeEvent.RequestOpenAppSettings)
        }
    }

    private fun onBackgroundAccessPermissionResponse(isEnabled: Boolean) {
        viewModelScope.launch {
            userSettings.setBackgroundAccessEnabled(isEnabled)
        }
    }

    private fun onStepGoalDismiss() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                showStepGoalDialog = false,
                selectedStepGoal = state.value.stepGoal
            )
        }
    }

    private fun onStepGoalConfirm() {
        viewModelScope.launch {
            userSettings.setStepGoal(state.value.selectedStepGoal)
            _state.value = _state.value.copy(
                showStepGoalDialog = false,
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
            showStepGoalDialog = true
        )
    }

    private fun onContinueBackgroundAccess() {
        _state.value = _state.value.copy(
            showBackgroundAccessRecommendedDialog = false
        )

        viewModelScope.launch {
            _event.send(HomeEvent.RequestBackgroundAccess)
        }
    }

    private fun onExitClick() {
        _state.value = _state.value.copy(
            showExitDialog = true
        )
    }

    private fun onExitConfirm() {
        stepCounterManager.stop()

        _state.value = _state.value.copy(
            showExitDialog = false
        )

        viewModelScope.launch {
            _event.send(HomeEvent.RequestAppClose)
        }
    }

    private fun onExitDismiss() {
        _state.value = _state.value.copy(
            showExitDialog = false
        )
    }
}