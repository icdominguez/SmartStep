package com.icdominguez.smartstep.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object PersonalSettings: Route, NavKey

    @Serializable
    data object Home: Route, NavKey

}