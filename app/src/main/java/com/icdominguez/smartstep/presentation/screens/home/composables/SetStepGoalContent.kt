package com.icdominguez.smartstep.presentation.screens.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.SmartStepWheelPicker
import com.icdominguez.smartstep.presentation.composables.buttons.PrimaryButton
import com.icdominguez.smartstep.presentation.composables.buttons.TextButton
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary
import com.icdominguez.smartstep.presentation.designsystem.TextSecondary

internal const val MIN_STEP_GOAL = 1000
internal const val MAX_STEP_GOAL = 40000
internal val STEP_GOAL_RANGE = (MIN_STEP_GOAL..MAX_STEP_GOAL step 1000).reversed()

@Composable
fun SetStepGoalContent(
    modifier: Modifier = Modifier,
    isTablet: Boolean = false,
    stepGoal: Int,
    onStepGoalChange: (Int) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(
                all = if(isTablet) 24.dp else 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.menu_step_goal),
            style = LocalSmartStepTypography.current.titleMedium,
            color = TextPrimary,
        )

        Spacer(
            modifier = Modifier
                .height(if(isTablet) 16.dp else 8.dp)
        )

        SmartStepWheelPicker(
            items = STEP_GOAL_RANGE.map { it },
            onSelected = { _, item ->
                onStepGoalChange(item)
            },
            initialSelectedIndex = STEP_GOAL_RANGE.indexOf(stepGoal),
        ) { item, isSelected ->
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "$item",
                    color = if (isSelected) TextPrimary else TextSecondary
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.button_save),
            onClick = { onConfirm() }
        )

        TextButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.button_cancel),
            onClick = { onDismiss() }
        )
    }
}