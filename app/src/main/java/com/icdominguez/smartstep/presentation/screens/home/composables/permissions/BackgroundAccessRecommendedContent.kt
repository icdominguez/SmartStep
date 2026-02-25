package com.icdominguez.smartstep.presentation.screens.home.composables.permissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.designsystem.composables.buttons.PrimaryButton
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary

@Composable
fun BackgroundAccessRecommendedContent(
    modifier: Modifier = Modifier,
    onContinueButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.modal_bottom_sheet_3_title),
            style = LocalSmartStepTypography.current.titleMedium,
            color = TextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
            .height(8.dp)
        )

        Text(
            text = stringResource(R.string.modal_bottom_sheet_3_description),
            style = LocalSmartStepTypography.current.bodyLargeRegular,
            color = TextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.button_continue),
            onClick = { onContinueButtonClick() }
        )
    }
}