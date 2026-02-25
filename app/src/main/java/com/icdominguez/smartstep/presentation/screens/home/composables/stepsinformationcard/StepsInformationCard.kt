package com.icdominguez.smartstep.presentation.screens.home.composables.stepsinformationcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.TextWhite

@Composable
fun StepsInformationCard(
    modifier: Modifier = Modifier,
    steps: Int,
    stepGoal: Int,
    progress: Float,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(
                all = 16.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = TextWhite.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(R.drawable.ic_sneakers),
                    contentDescription = null,
                    tint = TextWhite,
                )
            }

            Column {
                Text(
                    text = "$steps",
                    style = LocalSmartStepTypography.current.titleAccent,
                    color = TextWhite
                )
                Text(
                    text = "/$stepGoal ${stringResource(R.string.steps)}",
                    style = LocalSmartStepTypography.current.titleMedium,
                    color = TextWhite
                )
            }

            SmartStepVerticalProgressBar(
                progress = progress
            )
        }
    }
}

@Preview
@Composable
private fun StepsInformationCardPreview() {
    SmartStepTheme {
        StepsInformationCard(
            steps = 4523,
            stepGoal = 6000,
            progress = 0.2f
        )
    }
}