package com.icdominguez.smartstep.presentation.designsystem.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveOverlay(
    isTablet: Boolean,
    dismissOnClickOutside: Boolean = false,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (isTablet) {
        SmartStepCustomDialog(
            onDismiss = onDismiss,
            dismissOnClickOutside = dismissOnClickOutside,
        ) { content() }
    } else {
        SmartStepDefaultBottonSheet(
            onDismissRequest = onDismiss,
            dismissOnClickOutside = dismissOnClickOutside,
        ) { content() }
    }
}