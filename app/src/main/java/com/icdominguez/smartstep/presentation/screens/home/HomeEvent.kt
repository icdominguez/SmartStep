package com.icdominguez.smartstep.presentation.screens.home

sealed interface HomeEvent {
    data object RequestActivityRecognition : HomeEvent
    data object RequestIgnoreBatteryOptimizations : HomeEvent
    data object RequestOpenAppSettings: HomeEvent
}