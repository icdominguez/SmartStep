package com.icdominguez.smartstep.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.SmartStepCustomDialog
import com.icdominguez.smartstep.presentation.composables.SmartStepDefaultBottonSheet
import com.icdominguez.smartstep.presentation.designsystem.BackgroundSecondary
import com.icdominguez.smartstep.presentation.screens.home.composables.ExitDialog
import com.icdominguez.smartstep.presentation.screens.home.composables.HomeTopBar
import com.icdominguez.smartstep.presentation.screens.home.composables.NavigationDrawerItem
import com.icdominguez.smartstep.presentation.screens.home.composables.SetStepGoalContent
import com.icdominguez.smartstep.presentation.utils.DeviceConfiguration
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState = HomeState(),
    onAction: (HomeAction) -> Unit,
    onNavigateToPersonalSettings: () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var showExitDialog by remember { mutableStateOf(false) }
    var showStepGoalModalBottomSheet by remember { mutableStateOf(false) }
    val stepGoalModalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                windowInsets = WindowInsets(
                    top = 12.dp,
                    bottom = 12.dp,
                    left = 12.dp,
                    right = 12.dp
                ),
                drawerContainerColor = BackgroundSecondary,
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth(if (deviceType.isTablet()) 1f else 0.75f)
                        .fillMaxSize()
                ) {
                    NavigationDrawerItem(
                        text = stringResource(R.string.menu_step_goal),
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                showStepGoalModalBottomSheet = true
                            }
                        }
                    )
                    NavigationDrawerItem(
                        text = stringResource(R.string.personal_settings),
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                onNavigateToPersonalSettings()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        text = stringResource(R.string.menu_exit),
                        textColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                showExitDialog = true
                            }
                        }
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeTopBar(
                onShowDrawerMenuClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }

    if (showStepGoalModalBottomSheet) {
        if (deviceType.isTablet()) {
            SmartStepCustomDialog(
                onDismiss = {
                    showStepGoalModalBottomSheet = false
                }
            ) {
                SetStepGoalContent(
                    onConfirm = {
                        showStepGoalModalBottomSheet = false
                    },
                    onDismiss = {
                        showStepGoalModalBottomSheet = false
                    }
                )
            }
        } else {
            SmartStepDefaultBottonSheet(
                sheetState = stepGoalModalBottomSheetState,
                onDismissRequest = {
                    showStepGoalModalBottomSheet = false
                }
            ) {
                SetStepGoalContent(
                    onConfirm = {
                        showStepGoalModalBottomSheet = false
                    },
                    onDismiss = {
                        showStepGoalModalBottomSheet = false
                    }
                )
            }
        }
    }

    if(showExitDialog) {
        ExitDialog(
            onDismiss = { showExitDialog = false },
            onConfirm = { showExitDialog = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(),
        onAction = {},
        onNavigateToPersonalSettings = {}
    )
}

