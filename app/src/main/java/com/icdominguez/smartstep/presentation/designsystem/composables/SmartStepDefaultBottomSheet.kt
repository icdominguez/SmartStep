package com.icdominguez.smartstep.presentation.designsystem.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icdominguez.smartstep.presentation.designsystem.BackgroundSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartStepDefaultBottonSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    ),
    onDismissRequest: () -> Unit = {},
    dismissOnClickOutside: Boolean = false,
    content: @Composable () -> Unit,
) {
    val dragHandleComposable: (@Composable () -> Unit)? =
        if (dismissOnClickOutside) {
            { BottomSheetDefaults.DragHandle() }
        } else {
            null
        }

    ModalBottomSheet(
        modifier = modifier
            .wrapContentHeight(),
        onDismissRequest = onDismissRequest,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false,
            shouldDismissOnClickOutside = dismissOnClickOutside,
        ),
        sheetState = sheetState,
        sheetGesturesEnabled = dismissOnClickOutside,
        containerColor = BackgroundSecondary,
        dragHandle = dragHandleComposable
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(color = BackgroundSecondary),
        ) {
            content()
        }
    }
}