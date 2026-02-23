package com.icdominguez.smartstep.presentation.screens.personalsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.SmartStepPickerInput
import com.icdominguez.smartstep.presentation.composables.buttons.PrimaryButton
import com.icdominguez.smartstep.presentation.composables.dropdown.SmartStepDropDownMenu
import com.icdominguez.smartstep.presentation.designsystem.BackgroundWhite
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary
import com.icdominguez.smartstep.presentation.model.Gender
import com.icdominguez.smartstep.presentation.model.HeightUnit
import com.icdominguez.smartstep.presentation.model.WeightUnit
import com.icdominguez.smartstep.presentation.screens.personalsettings.composables.PersonalSettingsTopBar
import com.icdominguez.smartstep.presentation.screens.personalsettings.dialogs.HeightPickerDialog
import com.icdominguez.smartstep.presentation.screens.personalsettings.dialogs.WeightPickerDialog
import com.icdominguez.smartstep.presentation.utils.DeviceConfiguration
import com.icdominguez.smartstep.presentation.utils.ObserveAsEvents
import com.icdominguez.smartstep.presentation.utils.toUiText
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonalSettingsScreen(
    viewModel: PersonalSettingsViewModel = koinViewModel(),
    isOnBoarding: Boolean = false,
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onAction = viewModel::onAction

    var showHeightDialog by remember { mutableStateOf(false) }
    var showWeightDialog by remember { mutableStateOf(false) }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceType = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    ObserveAsEvents(
        flow = viewModel.event,
        onEvent = { event ->
            when (event) {
                is PersonalSettingsEvent.OnPersonalSettingsSaved -> {
                    if(isOnBoarding) {
                        onNavigateToHome()
                    } else {
                        onNavigateBack()
                    }
                }
            }
        }
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PersonalSettingsTopBar(
            shouldShowSkipButton = isOnBoarding,
            onSkipClicked = {
                onAction(PersonalSettingsAction.OnSkipButtonClicked)
            },
        )

        Column(
            modifier = Modifier
                .then(
                    if (deviceType.isTablet()) {
                        Modifier
                            .width(394.dp)
                            .padding(
                                top = 32.dp,
                                bottom = 16.dp
                            )
                    } else {
                        Modifier
                            .fillMaxSize()
                            .padding(
                                top = 32.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp,
                            )
                    }
                )
        ) {
            if(isOnBoarding) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    text = stringResource(R.string.my_profile_description),
                    style = LocalSmartStepTypography.current.bodyLargeRegular,
                    textAlign = TextAlign.Center,
                    color = TextPrimary,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .background(
                        color = BackgroundWhite,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(16.dp)
            ) {
                SmartStepDropDownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = stringResource(R.string.gender),
                    options = Gender.entries,
                    selectedOption = state.gender,
                    onOptionSelected = {
                        onAction(PersonalSettingsAction.SetGender(it))
                    },
                    optionLabel = { it.toUiText() },
                )

                SmartStepPickerInput(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = stringResource(R.string.height),
                    selectedValue = state.displayHeight,
                    onClick = { showHeightDialog = true }
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                SmartStepPickerInput(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = stringResource(R.string.weight),
                    selectedValue = state.displayWeight,
                    onClick = { showWeightDialog = true }
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(if(isOnBoarding) R.string.button_start else R.string.button_save),
                onClick = {
                    onAction(PersonalSettingsAction.OnStartButtonClicked)
                }
            )
        }
    }

    if (showHeightDialog) {
        HeightPickerDialog(
            selectedHeightUnit = state.selectedHeightUnit,
            height = if (state.selectedHeightFeet == 0) state.height else state.selectedHeightValue,
            selectedHeightFeet = state.selectedHeightFeet,
            selectedHeightInches = state.selectedHeightInches,
            onHeightUnitChange = {
                onAction(PersonalSettingsAction.SetHeightUnit(HeightUnit.fromLabel(it)))
            },
            onHeightValueChange = {
                onAction(PersonalSettingsAction.OnHeightValueChange(it))
            },
            onHeightFeetValueChange = {
                onAction(PersonalSettingsAction.OnHeightFeetValueChange(it))
            },
            onHeightInchesValueChange = {
                onAction(PersonalSettingsAction.OnHeightInchesValueChange(it))
            },
            onDismiss = {
                showHeightDialog = false
                onAction(PersonalSettingsAction.OnDismissHeightPicker)
            },
            onConfirm = {
                showHeightDialog = false
                onAction(PersonalSettingsAction.OnSetNewHeightValue)
            }
        )
    }

    if (showWeightDialog) {
        WeightPickerDialog(
            selectedWeightUnit = state.selectedWeightUnit,
            weight = if (state.selectedWeightValue == 0) state.weight else state.selectedWeightValue,
            onWeightUnitChange = {
                onAction(PersonalSettingsAction.SetWeightUnit(WeightUnit.fromLabel(it)))
            },
            onWeightValueChange = {
                onAction(PersonalSettingsAction.OnWeightValueChange(it))
            },
            onDismiss = {
                showWeightDialog = false
                onAction(PersonalSettingsAction.OnDismissWeightPicker)
            },
            onConfirm = {
                showWeightDialog = false
                onAction(PersonalSettingsAction.OnSetNewWeightValue)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalSettingsScreenPreview() {
    SmartStepTheme {
        PersonalSettingsScreen(
            onNavigateToHome = {},
            onNavigateBack = {}
        )
    }
}

