package com.icdominguez.smartstep.presentation.composables.buttons

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() },
        enabled = enabled,
    ) {
        Text(
            text = text,
            style = LocalSmartStepTypography.current.bodyLargeMedium,
        )
    }
}

@Preview
@Composable
private fun TextButtonPreview() {
    SmartStepTheme {
        TextButton(text = "Exit") {}
    }
}