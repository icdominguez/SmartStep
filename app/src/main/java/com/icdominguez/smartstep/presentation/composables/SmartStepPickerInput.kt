package com.icdominguez.smartstep.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.StrokeMain
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary
import com.icdominguez.smartstep.presentation.designsystem.TextSecondary

@Composable
fun SmartStepPickerInput(
    modifier: Modifier = Modifier,
    title: String,
    selectedValue: String,
    isExpanded: Boolean = false,
) {
    val roundedCornerShape = RoundedCornerShape(10.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = roundedCornerShape
            )
            .border(
                width = 1.dp,
                color = StrokeMain,
                shape = roundedCornerShape,
            )
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = title,
                style = LocalSmartStepTypography.current.bodySmallRegular,
                color = TextSecondary,
            )

            Text(
                text = selectedValue,
                style = LocalSmartStepTypography.current.bodyLargeRegular,
                color = TextPrimary,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            tint = TextPrimary,
            contentDescription = "Expand/Collapse",
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SmartStepInputPreview() {
    SmartStepTheme {
        SmartStepPickerInput(
            title = "Gender",
            selectedValue = "Male",
        )
    }
}