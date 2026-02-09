package com.icdominguez.smartstep.presentation.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ButtonPrimary,
    secondary = ButtonSecondary,
    onPrimary = TextWhite,
    background = BackgroundSecondary,
)

private val typography = Typography()

@Composable
fun SmartStepTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = typography,
        content = content
    )
}