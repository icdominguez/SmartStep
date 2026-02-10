package com.icdominguez.smartstep.presentation.composables.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
    ) {
        Text(
            text = text,
            style = LocalSmartStepTypography.current.bodyLargeMedium
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    SmartStepTheme {
        PrimaryButton(text = "Button") {}
    }
}