package com.icdominguez.smartstep.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icdominguez.smartstep.presentation.composables.DefaultScreen
import com.icdominguez.smartstep.presentation.composables.switcher.UnitSwitcher
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartStepTheme {
                DefaultScreen(
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Welcome to SmartStep 👟",
                                style = LocalSmartStepTypography.current.bodyLargeRegular,
                            )

                            val unitSwitcherOptionSelected = remember { mutableStateOf("kg") }

                            UnitSwitcher(
                                leftOption = "kg",
                                rightOption = "lbs",
                                selectedOption = unitSwitcherOptionSelected.value,
                                onOptionSelected = {
                                    unitSwitcherOptionSelected.value = it
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}
