package com.example.planet.presentation.ui.plogging.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.planet.TAG
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.PloggingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : ComponentActivity() {
    private val ploggingViewModel by viewModels<PloggingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ploggingViewModel
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                MapGraph(ploggingViewModel = ploggingViewModel) { ploggingId ->
                    showPloggingResultActivity(ploggingId)
                }
            }
        }
    }

    private fun showPloggingResultActivity(ploggingId: Int) {
        val intent = Intent(this, PloggingResultActivity::class.java).also {
            it.putExtra("ploggingId", ploggingId)
        }
        startActivity(intent)
        finish()
    }
}

