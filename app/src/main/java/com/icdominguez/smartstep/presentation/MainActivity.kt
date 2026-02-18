package com.icdominguez.smartstep.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icdominguez.smartstep.presentation.composables.DefaultScreen
import com.icdominguez.smartstep.presentation.designsystem.SmartStepTheme
import com.icdominguez.smartstep.presentation.navigation.NavigationRoot

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartStepTheme {
                DefaultScreen {
                    NavigationRoot()
                }
            }
        }
    }
}
