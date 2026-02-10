package com.icdominguez.smartstep.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icdominguez.smartstep.presentation.composables.DefaultScreen
import com.icdominguez.smartstep.presentation.composables.SmartStepPickerInput
import com.icdominguez.smartstep.presentation.composables.dropdown.SmartStepDropDownMenu
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
                        val unitSwitcherOptionSelected = remember { mutableStateOf("kg") }
                        val genderSelected = remember { mutableStateOf("Male") }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
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

                                UnitSwitcher(
                                    leftOption = "kg",
                                    rightOption = "lbs",
                                    selectedOption = unitSwitcherOptionSelected.value,
                                    onOptionSelected = {
                                        unitSwitcherOptionSelected.value = it
                                    }
                                )

                                SmartStepDropDownMenu(
                                    title = "Gender",
                                    options = listOf("Male", "Female"),
                                    selectedOption = genderSelected.value,
                                    onOptionSelected = {
                                        genderSelected.value = it
                                    }
                                )

                                SmartStepPickerInput(
                                    title = "Height",
                                    selectedValue = "160 cm",
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                SmartStepPickerInput(
                                    title = "Weight",
                                    selectedValue = "60 kg",
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
