package com.icdominguez.smartstep.presentation.screens.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.R
import com.icdominguez.smartstep.presentation.composables.buttons.PrimaryButton
import com.icdominguez.smartstep.presentation.designsystem.BackgroundWhite
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.StrokeMain
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary

@Composable
fun ActivityPermissionExplanationContent(
    modifier: Modifier = Modifier,
    onContinueButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(color = BackgroundWhite)
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = StrokeMain,
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(all = 10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_navigation),
                contentDescription = null,
            )
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
        )

        Text(
            text = stringResource(R.string.modal_bottom_sheet_1_text),
            style = LocalSmartStepTypography.current.titleMedium,
            color = TextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .height(44.dp)
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.modal_bottom_sheet_1_button),
            onClick = {
                onContinueButtonClick()
            }
        )
    }
}