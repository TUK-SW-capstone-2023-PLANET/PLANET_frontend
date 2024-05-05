package com.example.planet.presentation.ui.plogging.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.planet.TAG
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.PloggingResultViewModel
import com.example.planet.presentation.viewmodel.PloggingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PloggingResultActivity : ComponentActivity() {
    private val ploggingResultViewModel by viewModels<PloggingResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            ploggingResultViewModel.getPloggingInfo(intent.getIntExtra("ploggingId", 0))
        }
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                PloggingResultScreen()
            }
        }
    }
}
