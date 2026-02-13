package com.icdominguez.smartstep.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icdominguez.smartstep.presentation.composables.DefaultScreen
import com.icdominguez.smartstep.presentation.composables.SmartStepCustomDialog
import com.icdominguez.smartstep.presentation.composables.SmartStepPickerInput
import com.icdominguez.smartstep.presentation.composables.SmartStepWheelPicker
import com.icdominguez.smartstep.presentation.composables.buttons.PrimaryButton
import com.icdominguez.smartstep.presentation.composables.buttons.TextButton
import com.icdominguez.smartstep.presentation.composables.dropdown.SmartStepDropDownMenu
import com.icdominguez.smartstep.presentation.composables.switcher.UnitSwitcher
import com.icdominguez.smartstep.presentation.designsystem.LocalSmartStepTypography
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.designsystem.TextPrimary
import com.icdominguez.smartstep.presentation.designsystem.TextSecondary

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

                        var showDialog by remember { mutableStateOf(false) }

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

                                PrimaryButton(
                                    text = "Open dialog",
                                    onClick = { showDialog = true }
                                )

                                if(showDialog) {
                                    SmartStepCustomDialog(
                                        onDismiss = { showDialog = false },
                                    ) {
                                        Column {
                                            Column(
                                                modifier = Modifier
                                                    .padding(all = 24.dp)
                                            ) {
                                                Text(
                                                    text = "Height",
                                                    style = LocalSmartStepTypography.current.titleMedium,
                                                    color = TextPrimary,
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = "Used to calculate distance",
                                                    style = LocalSmartStepTypography.current.bodyMediumRegular,
                                                    color = TextSecondary,
                                                )

                                                Spacer(modifier = Modifier.height(16.dp))

                                                UnitSwitcher(
                                                    leftOption = "kg",
                                                    rightOption = "lbs",
                                                    selectedOption = unitSwitcherOptionSelected.value,
                                                    onOptionSelected = {
                                                        unitSwitcherOptionSelected.value = it
                                                    }
                                                )
                                            }

                                            SmartStepWheelPicker(
                                                items = (1..12).map { "Item $it" },
                                                onSelected = { i, _ -> Log.d("icd", "Selected $i") },
                                            ) { item, isSelected ->
                                                Text(
                                                    text = item,
                                                    color = if (isSelected) Color(0xFF1F2024) else Color(
                                                        0xFF6C6E71
                                                    )
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        top = 20.dp,
                                                        bottom = 20.dp,
                                                        end = 24.dp,
                                                    ),
                                            ) {
                                                Spacer(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                )

                                                TextButton(
                                                    text = "Cancel",
                                                    onClick = { showDialog = false }
                                                )

                                                TextButton(
                                                    text = "Ok",
                                                    onClick = { showDialog = false }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
