package com.icdominguez.smartstep.presentation.personalsettings.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.buttons.TextButton
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary

@Composable
fun PersonalSettingsTopBar(
    onSkipClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 6.dp,
                horizontal = 16.dp
            ),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(R.string.my_profile),
            style = LocalSmartStepTypography.current.titleMedium,
            color = TextPrimary,
        )

        TextButton(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            text = stringResource(R.string.skip),
            onClick = { onSkipClicked() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalSettingsTopBarPreview() {
    SmartStepTheme {
        PersonalSettingsTopBar(onSkipClicked = {})
    }
}