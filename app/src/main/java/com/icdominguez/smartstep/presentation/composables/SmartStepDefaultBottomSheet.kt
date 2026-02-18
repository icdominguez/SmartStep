package com.icdominguez.smartstep.presentation.composables

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.icdominguez.smartstep.presentation.designsystem.BackgroundSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartStepDefaultBottonSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier
            .wrapContentHeight(),
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        containerColor = BackgroundSecondary,
        dragHandle = null,
    ) {
        content()
    }
}