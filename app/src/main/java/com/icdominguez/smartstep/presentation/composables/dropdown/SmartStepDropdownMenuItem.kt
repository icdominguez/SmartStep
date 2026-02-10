package com.icdominguez.smartstep.presentation.composables.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.designsystem.BackgroundSecondary
import com.icdominguez.smartstep.presentation.designsystem.ButtonPrimary
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary

@Composable
fun SmartStepDropDownItem(
    text: String,
    isSelected: Boolean = false,
    onClick: (String) -> Unit
) {
    val roundedCornerShape = RoundedCornerShape(10.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(roundedCornerShape)
            .background(
                color = if(isSelected) BackgroundSecondary else Color.Transparent,
                shape = roundedCornerShape
            )
            .clickable(
                onClick = { onClick(text) }
            )
            .padding(
                vertical = 12.dp,
                horizontal = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = LocalSmartStepTypography.current.bodyLargeRegular,
            color = TextPrimary,
        )

        if(isSelected) {
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_selected),
                modifier = Modifier
                    .size(16.dp),
                tint = ButtonPrimary,
                contentDescription = "Check"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmartStepDropDownItemPreview() {
    SmartStepTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .padding(
                    vertical = 4.dp,
                    horizontal = 6.dp,
                )
        ) {
            SmartStepDropDownItem(
                text = "Male",
                isSelected = false,
                onClick = {},
            )

            SmartStepDropDownItem(
                text = "Female",
                isSelected = true,
                onClick = {},
            )
        }
    }
}