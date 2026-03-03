package com.icdominguez.smartstep.presentation.designsystem.composables

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun DefaultScreen(
    content: @Composable () -> Unit
) {
    Scaffold { innerPadding ->
        val view = LocalView.current

        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            content()
        }

        SideEffect {
            val window = (view.context as? Activity)?.window
            if (!view.isInEditMode && window != null) {
                WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars =
                    true
            }
        }
    }
}