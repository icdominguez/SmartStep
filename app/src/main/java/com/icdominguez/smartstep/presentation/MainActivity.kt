package com.icdominguez.smartstep.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icdominguez.smartstep.presentation.composables.DefaultScreen
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.navigation.NavigationRoot
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.state.isLoading
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartStepTheme {
                DefaultScreen {
                    if(!viewModel.state.isLoading) {
                        NavigationRoot(
                            isOnBoardingCompleted = viewModel.state.isOnBoardingCompleted,
                        )
                    }
                }
            }
        }
    }
}
