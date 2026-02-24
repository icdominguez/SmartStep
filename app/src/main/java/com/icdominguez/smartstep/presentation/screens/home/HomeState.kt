package com.icdominguez.smartstep.presentation.screens.home

data class HomeState(
    val steps: Int = 0,
    val stepGoal: Int = 0,
    val selectedStepGoal: Int = 0,
    val isBatteryOptIgnored: Boolean? = null,
    // region Dialogs
    val showPermissionExplanationSheet: Boolean = false,
    val showManualPermissionSheet: Boolean = false,
    val showBatteryRecommendedDialog: Boolean = false,
    val showStepGoalModalBottomSheet: Boolean = false,
    val showExitDialog: Boolean = false,
    // end region
)
