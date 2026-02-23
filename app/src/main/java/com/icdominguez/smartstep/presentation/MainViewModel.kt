package com.icdominguez.smartstep.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.smartstep.domain.UserSettings
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    userSettings: UserSettings
): ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        userSettings
            .getUserSettingsData()
            .distinctUntilChanged()
            .onEach { userSettings ->
                state = state.copy(
                    isOnBoardingCompleted = userSettings.gender.isNotEmpty(),
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}