package com.example.planet.presentation.ui.plogging.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.planet.R
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.plogging.PloggingResult
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.PloggingResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PloggingResultActivity : ComponentActivity() {
    private val ploggingResultViewModel by viewModels<PloggingResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            ploggingResultViewModel.getPloggingInfo(intent.getLongExtra("ploggingId", 0))
        }
        setContent {
            MyApplicationTheme {
                val ploggingInfoState =
                    ploggingResultViewModel.ploggingInfo.collectAsStateWithLifecycle().value
                if (ploggingInfoState is ApiState.Success<*>) {
                    PloggingResultScreen(ploggingInfo = (ploggingInfoState.value) as PloggingResult) {
                        startMainActivity(it)
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = colorResource(id = R.color.main_color1))
                    }
                }
            }
        }
    }

    private fun startMainActivity(isApiCall: Boolean = true) {
        finish()
    }
}
