package com.icdominguez.smartstep.presentation.composables.switcher

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.designsystem.ButtonSecondary
import com.icdominguez.smartstep.presentation.designsystem.OnSecondaryContainer
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.StrokeMain
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary

@Composable
fun SwitcherOption(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    isRightOption: Boolean = false,
    onClick: () -> Unit
) {
    val roundedCornersShape = if(isRightOption) {
        RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
    } else {
        RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50)
    }

    Box(
        modifier = modifier
            .height(40.dp)
            .clip(roundedCornersShape)
            .background(
                if(isSelected) ButtonSecondary else Color.Transparent
            )
            .border(
                width = 1.dp,
                color = StrokeMain,
                shape = roundedCornersShape
            )
            .clickable(
                onClick = { onClick() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if(isSelected) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.ic_selected),
                    contentDescription = "Selected",
                    tint = OnSecondaryContainer
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = if(isSelected) OnSecondaryContainer else TextPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SwitcherOptionPreview() {
    SmartStepTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SwitcherOption(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = "cm",
                isSelected = true,
                onClick = {}
            )

            SwitcherOption(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = "ft/in",
                isSelected = false,
                isRightOption = true,
                onClick = {}
            )
        }
    }
}