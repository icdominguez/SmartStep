package com.icdominguez.smartstep.presentation.screens.personalsettings.dialogs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.designsystem.composables.SmartStepCustomDialog
import com.icdominguez.smartstep.presentation.designsystem.composables.SmartStepWheelPicker
import com.icdominguez.smartstep.presentation.designsystem.composables.buttons.TextButton
import com.icdominguez.smartstep.presentation.designsystem.composables.switcher.UnitSwitcher
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary
import com.icdominguez.smartstep.presentation.designsystem.TextSecondary
import com.icdominguez.smartstep.domain.model.HeightUnit

private const val MIN_HEIGHT = 60
private const val MAX_HEIGHT = 250

private val HEIGHT_RANGE = (MIN_HEIGHT..MAX_HEIGHT)


@Composable
fun HeightPickerDialog(
    modifier: Modifier = Modifier,
    selectedHeightUnit: HeightUnit,
    height: Int,
    selectedHeightFeet: Int,
    selectedHeightInches: Int,
    onHeightUnitChange: (String) -> Unit,
    onHeightValueChange: (Int) -> Unit,
    onHeightFeetValueChange: (Int) -> Unit,
    onHeightInchesValueChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    SmartStepCustomDialog(
        showPadding = false,
        onDismiss = {
            onDismiss()
        }
    ) {
        Column {
            Column(
                modifier = Modifier
                    .padding(all = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.height),
                    style = LocalSmartStepTypography.current.titleMedium,
                    color = TextPrimary,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.height_description),
                    style = LocalSmartStepTypography.current.bodyLargeRegular,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                UnitSwitcher(
                    leftOption = HeightUnit.CENTIMETERS.label,
                    rightOption = HeightUnit.FEET.label,
                    selectedOption = selectedHeightUnit.label,
                    onOptionSelected = {
                        onHeightUnitChange(it)
                    }
                )
            }

            when (selectedHeightUnit) {
                HeightUnit.CENTIMETERS -> {
                    SmartStepWheelPicker(
                        items = HEIGHT_RANGE.map { it },
                        onSelected = { index, item ->
                            onHeightValueChange(item)
                            Log.d("HeightPickerDialog", "Selected index: $index, item: $item")
                        },
                        initialSelectedIndex = height - MIN_HEIGHT,
                    ) { item, isSelected ->
                        Text(
                            text = "$item  ${selectedHeightUnit.label}",
                            style = LocalSmartStepTypography.current.titleMedium.copy(
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            ),
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                    }
                }

                HeightUnit.FEET -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SmartStepWheelPicker(
                            modifier = Modifier
                                .weight(1f),
                            items = (0..9).map { it },
                            onSelected = { index, item ->
                                onHeightFeetValueChange(item)
                                Log.d("HeightPickerDialog", "Selected index: $index, item: $item")
                            },
                            initialSelectedIndex = selectedHeightFeet,
                        ) { item, isSelected ->
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 24.dp
                                    ),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "$item",
                                    style = LocalSmartStepTypography.current.titleMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) TextPrimary else TextSecondary
                                )

                                Spacer(
                                    modifier = Modifier
                                        .weight(1f)
                                )

                                if (isSelected) {
                                    Text(
                                        text = "ft",
                                        style = LocalSmartStepTypography.current.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                        ),
                                        color = TextPrimary
                                    )
                                }
                            }
                        }

                        SmartStepWheelPicker(
                            modifier = Modifier
                                .weight(1f),
                            items = (0..9).map { it },
                            onSelected = { index, item ->
                                onHeightInchesValueChange(item)
                                Log.d("HeightPickerDialog", "Selected index: $index, item: $item")
                            },
                            initialSelectedIndex = selectedHeightInches,
                        ) { item, isSelected ->
                            Row(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 24.dp
                                    ),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "$item",
                                    style = LocalSmartStepTypography.current.titleMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) TextPrimary else TextSecondary
                                )

                                Spacer(
                                    modifier = Modifier
                                        .weight(1f)
                                )

                                if (isSelected) {
                                    Text(
                                        text = "in",
                                        style = LocalSmartStepTypography.current.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                        ),
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 20.dp,
                        bottom = 20.dp,
                        end = 24.dp
                    )
            ) {

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                TextButton(
                    text = stringResource(R.string.button_cancel),
                    onClick = { onDismiss() },
                )

                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                )

                TextButton(
                    text = stringResource(R.string.button_ok),
                    onClick = { onConfirm() },
                )
            }
        }
    }
}