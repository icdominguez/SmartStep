package com.icdominguez.smartstep.presentation.screens.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.buttons.PrimaryButton
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary
import com.icdominguez.smartstep.presentation.designsystem.TextSecondary

@Composable
fun ManualPermissionContent(
    modifier: Modifier = Modifier,
    onOpenSettingsClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.modal_bottom_sheet_2_title),
            style = LocalSmartStepTypography.current.titleMedium,
            color = TextPrimary,
        )

        Spacer(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = stringResource(R.string.modal_bottom_sheet_2_description),
            style = LocalSmartStepTypography.current.bodyLargeRegular,
            color = TextSecondary,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.modal_bottom_sheet_2_step_1),
                style = LocalSmartStepTypography.current.bodyLargeMedium,
                color = TextPrimary,
            )
            Text(
                text = stringResource(R.string.modal_bottom_sheet_2_step_2),
                style = LocalSmartStepTypography.current.bodyLargeMedium,
                color = TextPrimary,
            )
            Text(
                text = stringResource(R.string.modal_bottom_sheet_2_step_3),
                style = LocalSmartStepTypography.current.bodyLargeMedium,
                color = TextPrimary,
            )
        }

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.modal_bottom_sheet_2_button),
            onClick = { onOpenSettingsClick() }
        )
    }
}