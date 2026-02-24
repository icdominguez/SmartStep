package com.icdominguez.smartstep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.icdominguez.smartstep.presentation.screens.home.HomeScreen
import com.icdominguez.smartstep.presentation.screens.home.HomeViewModel
import com.icdominguez.smartstep.presentation.screens.personalsettings.PersonalSettingsScreen
import com.icdominguez.smartstep.presentation.screens.personalsettings.PersonalSettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    isOnBoardingCompleted: Boolean,
) {
    val startDestination = if (isOnBoardingCompleted) Route.Home else Route.PersonalSettings(true)
    val backStack = rememberNavBackStack(startDestination)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when(key) {
                is Route.PersonalSettings -> {
                    NavEntry(key) {
                        val viewModel = koinViewModel<PersonalSettingsViewModel>()

                        PersonalSettingsScreen(
                            viewModel = viewModel,
                            onNavigateToHome = {
                                backStack.add(Route.Home)
                                backStack.remove(key)
                            },
                            isOnBoarding = key.isFromOnBoarding,
                            onNavigateBack = {
                                backStack.remove(Route.PersonalSettings())
                            }
                        )
                    }
                }
                is Route.Home -> {
                    NavEntry(key) {
                        val viewModel = koinViewModel<HomeViewModel>()

                        HomeScreen(
                            viewModel = viewModel,
                            onNavigateToPersonalSettings = {
                                backStack.add(Route.PersonalSettings())
                            },
                        )
                    }
                }
                else -> error("Unknown navKey: $key")
            }
        }
    )
}
