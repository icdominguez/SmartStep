package com.icdominguez.smartstep.presentation.screens.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.SmartStepCustomDialog
import com.icdominguez.smartstep.presentation.composables.SmartStepDefaultBottonSheet
import com.icdominguez.smartstep.presentation.designsystem.BackgroundSecondary
import com.icdominguez.smartstep.presentation.screens.home.composables.ActivityPermissionExplanationContent
import com.icdominguez.smartstep.presentation.screens.home.composables.AdaptiveOverlay
import com.icdominguez.smartstep.presentation.screens.home.composables.BackgroundAccessRecommendedContent
import com.icdominguez.smartstep.presentation.screens.home.composables.ExitDialog
import com.icdominguez.smartstep.presentation.screens.home.composables.HomeTopBar
import com.icdominguez.smartstep.presentation.screens.home.composables.ManualPermissionContent
import com.icdominguez.smartstep.presentation.screens.home.composables.NavigationDrawerItem
import com.icdominguez.smartstep.presentation.screens.home.composables.SetStepGoalContent
import com.icdominguez.smartstep.presentation.utils.DeviceConfiguration
import com.icdominguez.smartstep.presentation.utils.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    onNavigateToPersonalSettings: () -> Unit,
) {
    // region ViewModelSetup
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onAction = viewModel::onAction
    // end region

    val context = LocalContext.current
    val activity = context as Activity

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // region Adaptive
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
    val isTablet = deviceType.isTablet()
    // end region

    // region PermissionLaunchers
    val activityRecognitionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            onAction(HomeAction.OnActivityRecognitionGranted)
        } else {
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACTIVITY_RECOGNITION
            )

            onAction(HomeAction.OnActivityRecognitionDenied(shouldShowRationale))
        }
    }

    val appSettingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            onAction(HomeAction.OnActivityRecognitionChecked(true))
        }
    }

    val ignoreBatteryOptimizationsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val isIgnoring = pm.isIgnoringBatteryOptimizations(context.packageName)
        onAction(HomeAction.OnIgnoreBatteryOptimizationsResponse(isIgnoring))
    }
    // end region

    LaunchedEffect(Unit) {
        val isActivityRecognitionPermissionGranted =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) true
            else ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED

        onAction(HomeAction.OnActivityRecognitionChecked(isActivityRecognitionPermissionGranted))
    }

    ObserveAsEvents(
        flow = viewModel.event,
        onEvent = { event ->
            when(event) {
                is HomeEvent.RequestOpenAppSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    appSettingsLauncher.launch(intent)
                }
                is HomeEvent.RequestActivityRecognition -> {
                    activityRecognitionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
                }
                is HomeEvent.RequestIgnoreBatteryOptimizations -> {
                    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                        data = "package:${context.packageName}".toUri()
                    }
                    ignoreBatteryOptimizationsLauncher.launch(intent)
                }
            }
        }
    )

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
                    if(state.isBatteryOptIgnored != true) {
                        NavigationDrawerItem(
                            text = stringResource(R.string.menu_fix_step_issue),
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    onAction(HomeAction.OnActivityRecognitionGranted)
                                }
                            }
                        )
                    }

                    NavigationDrawerItem(
                        text = stringResource(R.string.menu_step_goal),
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                onAction(HomeAction.OnShowStepGoalDialog)
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
                                onAction(HomeAction.OnExitClick)
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

    if (state.showStepGoalModalBottomSheet) {
        if (deviceType.isTablet()) {
            SmartStepCustomDialog(
                onDismiss = {}
            ) {
                SetStepGoalContent(
                    stepGoal = state.stepGoal,
                    onStepGoalChange = {
                        onAction(HomeAction.OnStepGoalChange(it))
                    },
                    onConfirm = {
                        onAction(HomeAction.OnStepGoalConfirm)
                    },
                    onDismiss = {
                        onAction(HomeAction.OnStepGoalDismiss)
                    }
                )
            }
        } else {
            SmartStepDefaultBottonSheet {
                SetStepGoalContent(
                    stepGoal = state.stepGoal,
                    onStepGoalChange = {
                        onAction(HomeAction.OnStepGoalChange(it))
                    },
                    onConfirm = {
                        onAction(HomeAction.OnStepGoalConfirm)
                    },
                    onDismiss = {
                        onAction(HomeAction.OnStepGoalDismiss)
                    }
                )
            }
        }
    }

    if(state.showExitDialog) {
        ExitDialog(
            onDismiss = { onAction(HomeAction.OnExitDismiss) },
            onConfirm = { onAction(HomeAction.OnExitConfirm) }
        )
    }

    if(state.showPermissionExplanationSheet) {
        AdaptiveOverlay(
            isTablet = isTablet,
            onDismiss = { }
        ) {
            ActivityPermissionExplanationContent(
                onContinueButtonClick = {
                    onAction(HomeAction.OnActivityRecognitionRequest)
                }
            )
        }
    }

    if(state.showManualPermissionSheet) {
        AdaptiveOverlay(
            isTablet = isTablet,
            onDismiss = { }
        ) {
            ManualPermissionContent(
                onOpenSettingsClick = {
                    onAction(HomeAction.OnOpenManualSettings)
                }
            )
        }
    }

    if(state.showBatteryRecommendedDialog) {
        AdaptiveOverlay(
            isTablet = isTablet,
            dismissOnClickOutside = true,
            onDismiss = {
            }
        ) {
            BackgroundAccessRecommendedContent(
                onContinueButtonClick = {
                    onAction(HomeAction.OnContinueBackgroundAccess)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onNavigateToPersonalSettings = {}
    )
}

