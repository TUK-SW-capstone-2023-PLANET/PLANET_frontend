package com.example.planet.presentation.ui.main.record.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.MainScreen
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.TierScreen
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.ui.main.record.component.RecordBottomNavigation
import com.example.planet.presentation.ui.main.record.screen.map.screen.MapScreen
import com.example.planet.presentation.ui.main.record.screen.record.screen.RecordScreen
import com.example.planet.presentation.ui.main.record.screen.ScoreScreen
import com.example.planet.presentation.ui.main.record.screen.StatisticsScreen
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.viewmodel.RecordViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordNavigationGraph(
    mainViewModel: MainViewModel,
    recordViewModel: RecordViewModel,
    startMapActivity: () -> Unit,
    startPloggingResultActivity: (Long, String) -> Unit,
    startSearchActivity: () -> Unit,
    startRankingActivity: (String) -> Unit
) {
    Log.d(TAG, "RecordNavigationGraph 리컴포지션")


    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            bottomBar = {
                RecordBottomNavigation(navController = navController)
            }
        ) { paddingValue ->
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(paddingValue),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = BottomNavItem.HomeScreen.screenRoute
                ) {
                    composable(BottomNavItem.HomeScreen.screenRoute) {
                        MainScreen(
                            navController = navController,
                            mainViewModel = mainViewModel,
                            startMapActivity = { startMapActivity() },
                        ) { startRankingActivity(it) }
                    }
                    composable(BottomNavItem.RecordScreen.screenRoute) {
                        RecordScreen(
                            recordViewModel = recordViewModel,
                            startPloggingResultActivity = { ploggingId, theme ->
                                startPloggingResultActivity(
                                    ploggingId,
                                    theme
                                )
                            }
                        )
                    }
                    composable(BottomNavItem.StatisticsScreen.screenRoute) {
                        StatisticsScreen(recordViewModel = recordViewModel)
                    }
                    composable(BottomNavItem.ScoreScreen.screenRoute) {
                        ScoreScreen()
                    }
                    composable(BottomNavItem.MapScreen.screenRoute) {
                        MapScreen(
                            trashCans = recordViewModel.trashCans,
                            hotPlaces = recordViewModel.hotPlaces,
                            searchPlace = recordViewModel.searchResultPlace,
                            initSearchPlace = {recordViewModel.searchResultPlace = null},
                            readAllTrashCan = { recordViewModel.readAllTrashCanLocation() },
                            readAllHotPlace = { recordViewModel.readAllHotPlaceLocation() },
                            startSearchActivity = { startSearchActivity() },
                            setLocation = { recordViewModel.currentLatLng = it },
                            onTakePicture = { recordViewModel.currentLatLng?.let { recordViewModel.postTrashCanImage(recordViewModel.userId, it) } }
                        )
                    }
                    composable(ScreenNav.TierScreen.screenRoute) {
                        TierScreen(tierList = mainViewModel.tierList.value)
                    }
                }
            }
        }
    }

}