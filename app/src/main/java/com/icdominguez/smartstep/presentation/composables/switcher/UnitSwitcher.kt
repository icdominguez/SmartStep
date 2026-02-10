package com.icdominguez.smartstep.presentation.composables.switcher

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UnitSwitcher(
    modifier: Modifier = Modifier,
    leftOption: String,
    rightOption: String,
    selectedOption: String = leftOption,
    onOptionSelected: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        SwitcherOption(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = leftOption,
            isSelected = selectedOption == leftOption,
            onClick = { onOptionSelected(leftOption) },
        )

        SwitcherOption(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = rightOption,
            isSelected = selectedOption == rightOption,
            isRightOption = true,
            onClick = { onOptionSelected(rightOption) },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitSwitcherPreview() {
    UnitSwitcher(
        leftOption = "kg",
        rightOption = "lbs",
        onOptionSelected = {}
    )
}