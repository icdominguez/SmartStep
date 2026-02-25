package com.icdominguez.smartstep.presentation.screens.home

sealed interface HomeEvent {
    data object RequestActivityRecognition : HomeEvent
    data object RequestBackgroundAccess : HomeEvent
    data object RequestOpenAppSettings: HomeEvent
    data object RequestAppClose: HomeEvent
}