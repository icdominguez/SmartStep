package com.icdominguez.smartstep.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.icdominguez.smartstep.presentation.composables.DefaultScreen
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.personalsettings.PersonalSettingsScreen
import com.icdominguez.smartstep.presentation.personalsettings.PersonalSettingsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartStepTheme {
                val viewModel = koinViewModel<PersonalSettingsViewModel>()

                DefaultScreen {
                    PersonalSettingsScreen(
                        state = viewModel.state.collectAsStateWithLifecycle().value,
                        onAction = viewModel::onAction
                    )
                }
            }
        }
    }
}
