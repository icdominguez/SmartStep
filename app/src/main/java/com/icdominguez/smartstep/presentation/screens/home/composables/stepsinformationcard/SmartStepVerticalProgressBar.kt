package com.icdominguez.smartstep.presentation.screens.home.composables.stepsinformationcard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.TextWhite

@Composable
fun SmartStepVerticalProgressBar(
    modifier: Modifier = Modifier,
    progress: Float
) {
    val clampedProgress = progress.coerceIn(0f, 1f)

    val trackColor = TextWhite.copy(alpha = 0.2f)
    val progressColor = TextWhite

    val trackHeightDp = 12.dp
    val progressHeightDp = 8.dp

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(trackHeightDp)
    ) {
        val trackHeight = trackHeightDp.toPx()
        val progressHeight = progressHeightDp.toPx()
        val cornerRadius = trackHeight / 2f

        drawRoundRect(
            color = trackColor,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)

        )

        val progressWidth = size.width * clampedProgress
        val progressTop = (trackHeight - progressHeight) / 2f

        if (progressWidth > 0f) {
            val horizontalPadding = 2.dp.toPx()
            val progressCornerRadius = progressHeight / 2f

            val availableWidth = size.width - (horizontalPadding * 2)
            val paddedProgressWidth = (availableWidth * clampedProgress).coerceAtLeast(0f)

            drawRoundRect(
                color = progressColor,
                topLeft = Offset(horizontalPadding, progressTop),
                size = Size(paddedProgressWidth, progressHeight),
                cornerRadius = CornerRadius(progressCornerRadius, progressCornerRadius)
            )
        }
    }
}

@Preview
@Composable
private fun SmartStepVerticalProgressBarPreview() {
    SmartStepTheme {
        SmartStepVerticalProgressBar(
            progress = 1f,
        )
    }
}