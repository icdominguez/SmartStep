package com.icdominguez.smartstep.presentation.screens.home.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.icdominguez.smartstep.presentation.composables.SmartStepCustomDialog
import com.icdominguez.smartstep.presentation.composables.SmartStepDefaultBottonSheet

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