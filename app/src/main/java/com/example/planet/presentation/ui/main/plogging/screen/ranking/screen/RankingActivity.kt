package com.example.planet.presentation.ui.main.plogging.screen.ranking.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.planet.R
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.plogging.PloggingResult
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.SeasonScreen
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.TestScreen
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.UniversityScreen
import com.example.planet.presentation.ui.plogging.screen.PloggingResultScreen
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.viewmodel.PloggingResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RankingActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra("type") ?: "planet"

        setContent {
            MyApplicationTheme {
                when(type) {
                    "planet" -> {
                        PlanetRankingScreen(mainViewModel = mainViewModel) {finish()}
                    }
                    "university" -> {
                        UniversityRankingScreen(mainViewModel = mainViewModel) {finish()}
                    }
                    "season" -> {
                        SeasonRankingScreen(mainViewModel = mainViewModel) {finish()}
                    }
                    "universityIndividual" -> {
                        UniversityIndividualRankingScreen(mainViewModel = mainViewModel) {finish()}
                    }
                }
            }
        }
    }
}