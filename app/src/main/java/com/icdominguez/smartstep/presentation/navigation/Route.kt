package com.icdominguez.smartstep.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data class PersonalSettings(val isFromOnBoarding: Boolean = false): Route, NavKey

    @Serializable
    data object Home: Route, NavKey

}