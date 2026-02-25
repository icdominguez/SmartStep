package com.icdominguez.smartstep.presentation.screens.home

sealed interface HomeAction {
    // region Permissions
    data class OnActivityRecognitionChecked(val isGranted: Boolean) : HomeAction
    data object OnActivityRecognitionRequest : HomeAction
    data object OnActivityRecognitionGranted : HomeAction
    data class OnActivityRecognitionDenied(val showRationale: Boolean) : HomeAction
    // end region
    data class OnBackgroundAccessPermissionResponse(val isEnabled: Boolean): HomeAction
    data object OnOpenManualSettings : HomeAction
    data object OnContinueBackgroundAccess : HomeAction
    // end region
    // region StepGoal
    data object OnShowStepGoalDialog : HomeAction
    data class OnStepGoalChange(val stepGoal: Int) : HomeAction
    data object OnStepGoalConfirm : HomeAction
    data object OnStepGoalDismiss : HomeAction
    // end region
    data object OnExitClick: HomeAction
    data object OnExitConfirm: HomeAction
    data object OnExitDismiss: HomeAction
}