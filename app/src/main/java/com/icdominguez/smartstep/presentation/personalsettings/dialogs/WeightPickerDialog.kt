package com.icdominguez.smartstep.presentation.personalsettings.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.SmartStepCustomDialog
import com.icdominguez.smartstep.presentation.composables.SmartStepWheelPicker
import com.icdominguez.smartstep.presentation.composables.buttons.TextButton
import com.icdominguez.smartstep.presentation.composables.switcher.UnitSwitcher
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary
import com.icdominguez.smartstep.presentation.designsystem.TextSecondary
import com.icdominguez.smartstep.presentation.model.WeightUnit

private const val MIN_WEIGHT = 40
private const val MAX_WEIGHT = 250

private const val MIN_WEIGHT_POUNDS = 88
private const val MAX_WEIGHT_POUNDS = 551

private val KG_RANGE = (MIN_WEIGHT..MAX_WEIGHT)
private val POUNDS_RANGE = (MIN_WEIGHT_POUNDS..MAX_WEIGHT_POUNDS)

@Composable
fun WeightPickerDialog(
    modifier: Modifier = Modifier,
    selectedWeightUnit: WeightUnit,
    weight: Int,
    onWeightUnitChange: (String) -> Unit,
    onWeightValueChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    SmartStepCustomDialog(
        modifier = modifier,
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
                    text = stringResource(R.string.weight),
                    style = LocalSmartStepTypography.current.titleMedium,
                    color = TextPrimary,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.weight_description),
                    style = LocalSmartStepTypography.current.bodyLargeRegular,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                UnitSwitcher(
                    leftOption = WeightUnit.KILOS.label,
                    rightOption = WeightUnit.POUNDS.label,
                    selectedOption = selectedWeightUnit.label,
                    onOptionSelected = {
                        onWeightUnitChange(it)
                    }
                )
            }

            when(selectedWeightUnit) {
                WeightUnit.KILOS -> {
                    SmartStepWheelPicker(
                        items = KG_RANGE.map { it },
                        onSelected = { _, item ->
                            onWeightValueChange(item)
                        },
                        initialSelectedIndex = weight - MIN_WEIGHT,
                    ) { item, isSelected ->
                        Text(
                            text = "$item ${selectedWeightUnit.label}",
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                    }
                }
                WeightUnit.POUNDS -> {
                    SmartStepWheelPicker(
                        items = POUNDS_RANGE.map { it },
                        onSelected = { _, item ->
                            onWeightValueChange(item)
                        },
                        initialSelectedIndex = weight - MIN_WEIGHT_POUNDS,
                    ) { item, isSelected ->
                        Text(
                            text = "$item ${selectedWeightUnit.label}",
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
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

                Spacer(modifier = Modifier
                    .weight(1f))

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