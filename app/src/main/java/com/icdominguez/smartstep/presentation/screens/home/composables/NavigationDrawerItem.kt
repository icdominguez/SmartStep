package com.icdominguez.smartstep.presentation.screens.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.StrokeMain
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary

@Composable
fun NavigationDrawerItem(
    text: String,
    textColor: Color = TextPrimary,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable { onClick() },
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = text,
            color = textColor,
            style = LocalSmartStepTypography.current.bodyLargeMedium,
        )

        HorizontalDivider(
            color = StrokeMain
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationDrawerItemPreview() {
    SmartStepTheme {
        NavigationDrawerItem(
            text = "Step goal",
            onClick = {}
        )
    }
}