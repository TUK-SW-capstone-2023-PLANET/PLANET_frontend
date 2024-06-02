package com.example.planet.presentation.ui.main.record.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.MainScreen
import com.example.planet.presentation.ui.main.record.component.RecordBottomNavigation
import com.example.planet.presentation.ui.main.record.screen.MapScreen
import com.example.planet.presentation.ui.main.record.screen.record.screen.RecordScreen
import com.example.planet.presentation.ui.main.record.screen.ScoreScreen
import com.example.planet.presentation.ui.main.record.screen.StatisticsScreen
import com.example.planet.presentation.ui.main.record.theme.RECORDTheme
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.viewmodel.RecordViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordNavigationGraph(
    mainViewModel: MainViewModel,
    recordViewModel: RecordViewModel,
    startMapActivity: () -> Unit,
    startPloggingResultActivity: (Long) -> Unit
) {
    Log.d(TAG, "RecordNavigationGraph 리컴포지션")

    val navController = rememberNavController()
    RECORDTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    RecordBottomNavigation(navController = navController)
                }
            ) { paddingValue ->
                Column(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                        .padding(paddingValue),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.HomeScreen.screenRoute
                    ) {
                        composable(BottomNavItem.HomeScreen.screenRoute) {
                            MainScreen(
                                navController = navController,
                                mainViewModel = mainViewModel
                            ) { startMapActivity() }
                        }
                        composable(BottomNavItem.RecordScreen.screenRoute) {
                            RecordScreen(
                                recordViewModel = recordViewModel,
                                startPloggingResultActivity = {startPloggingResultActivity(it)}
                            )
                        }
                        composable(BottomNavItem.StatisticsScreen.screenRoute) {
                            StatisticsScreen()
                        }
                        composable(BottomNavItem.ScoreScreen.screenRoute) {
                            ScoreScreen()
                        }
                        composable(BottomNavItem.MapScreen.screenRoute) {
                            MapScreen()
                        }

                    }
                }
            }
        }
    }

}