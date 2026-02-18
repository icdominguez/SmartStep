package com.icdominguez.smartstep.presentation.screens.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.SmartStepCustomDialog
import com.icdominguez.smartstep.presentation.composables.buttons.PrimaryButton
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.TextSecondary

@Composable
fun ExitDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    SmartStepCustomDialog(
        onDismiss = { onDismiss() }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(34.dp),
                painter = painterResource(R.drawable.ic_power_turn_on),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.exit_message),
                style = LocalSmartStepTypography.current.bodyLargeRegular,
                textAlign = TextAlign.Center,
                color = TextSecondary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.button_ok),
                onClick = { onConfirm() }
            )
        }
    }
}