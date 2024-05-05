package com.example.planet.presentation.ui.plogging.screen

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PloggingResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("activityResultRegistry = ${activityResultRegistry}")
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                PloggingResultScreen()
            }
        }
    }
}
