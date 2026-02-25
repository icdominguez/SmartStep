package com.icdominguez.smartstep.presentation.designsystem.composables.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.presentation.designsystem.composables.SmartStepPickerInput
import com.icdominguez.smartstep.presentation.designsystem.BackgroundWhite
import com.icdominguez.smartstep.domain.model.Gender
import com.icdominguez.smartstep.presentation.utils.UiText
import com.icdominguez.smartstep.presentation.utils.toUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SmartStepDropDownMenu(
    modifier: Modifier = Modifier,
    title: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    optionLabel: (T) -> UiText,
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        },
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .menuAnchor(
                type = ExposedDropdownMenuAnchorType.PrimaryEditable,
                enabled = true
            ),
        ) {
            SmartStepPickerInput(
                title = title,
                selectedValue = optionLabel(selectedOption).asString(),
                isExpanded = isExpanded,
            )
        }

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            shape = RoundedCornerShape(8.dp),
            containerColor = BackgroundWhite,
        ) {
            Column(
                modifier = modifier
                    .background(
                        color = BackgroundWhite,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 8.dp)
            ) {
                options.map { option ->
                    SmartStepDropDownItem(
                        text = optionLabel(option).asString(),
                        isSelected = option == selectedOption,
                        onClick = {
                            onOptionSelected(option)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmartStepDropDownMenuPreview() {
    SmartStepDropDownMenu(
        title = "Gender",
        options = Gender.entries,
        selectedOption = Gender.MALE,
        onOptionSelected = {},
        optionLabel = { Gender.FEMALE.toUiText() }
    )
}