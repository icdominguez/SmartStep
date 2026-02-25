package com.icdominguez.smartstep.presentation.designsystem.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.icdominguez.smartstep.presentation.designsystem.BackgroundSecondary

@Composable
fun SmartStepCustomDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    dismissOnClickOutside: Boolean = false,
    showPadding: Boolean = true,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = dismissOnClickOutside,
        )
    ) {
        Surface(
            modifier = modifier
                .width(328.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            color = BackgroundSecondary,
            tonalElevation = 8.dp,
        ) {
            Box(
                modifier = Modifier
                    .padding(all = if(showPadding) 24.dp else 0.dp)
                    .background(
                        color = BackgroundSecondary
                    )
            ) {
                content()
            }
        }
    }
}