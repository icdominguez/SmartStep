package com.icdominguez.smartstep.presentation.screens.home

data class HomeState(
    val steps: Int = 0,
    val stepGoal: Int = 0,
    val selectedStepGoal: Int = 0,
    val isBackgroundAccessEnabled: Boolean? = null,
    // region Dialogs
    val showMotionSensorsDialog: Boolean = false,
    val showEnableAccessManuallyDialog: Boolean = false,
    val showBackgroundAccessRecommendedDialog: Boolean = false,
    val showStepGoalDialog: Boolean = false,
    val showExitDialog: Boolean = false,
    // end region
)
