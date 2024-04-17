package com.example.planet.presentation.ui.main.plogging.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.TAG
import com.example.planet.component.main.MainTopSwitch
import com.example.planet.presentation.ui.main.plogging.component.BottomNavigation
import com.example.planet.presentation.ui.main.plogging.screen.community.CommunityScreen
import com.example.planet.presentation.ui.main.plogging.screen.home.MainScreen
import com.example.planet.presentation.ui.main.plogging.screen.home.TierScreen
import com.example.planet.presentation.ui.main.plogging.screen.message.MessageScreen
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.ui.main.plogging.screen.ranking.screen.PlanetRankingScreen
import com.example.planet.presentation.ui.main.plogging.screen.ranking.screen.RankingScreen
import com.example.planet.presentation.ui.main.plogging.screen.ranking.screen.SeasonRankingScreen
import com.example.planet.presentation.ui.main.plogging.screen.ranking.screen.UniversityIndividualRankingScreen
import com.example.planet.presentation.ui.main.plogging.screen.ranking.screen.UniversityRankingScreen
import com.example.planet.presentation.ui.main.plogging.screen.user.UserScreen
import com.example.planet.presentation.viewmodel.MainViewModel


@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel = viewModel(),
    startMapActivity: () -> Unit,
    startUserActivity: () -> Unit
) {

    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            if(mainViewModel.mainTopSwitchIsShow.value) {
//                MainTopSwitch(mainViewModel = mainViewModel)
//            }

            NavHost(
                navController = navController,
                startDestination = BottomNavItem.HomeScreen.screenRoute
            ) {
                composable(BottomNavItem.HomeScreen.screenRoute) {
                    MainScreen(navController = navController, mainViewModel = mainViewModel) { startMapActivity() }
                }
                composable(BottomNavItem.RankingScreen.screenRoute) {
                    RankingScreen(navController = navController, mainViewModel = mainViewModel)
                }
                composable(BottomNavItem.UserScreen.screenRoute) {
                    Log.d(TAG,"seasonUser: ${mainViewModel.mySeasonRank}, universityInfo: ${mainViewModel.myUniversityInfo}")
                    mainViewModel.myUniversityInfo.value?.let { university ->
                        mainViewModel.mySeasonRank.value?.let { seasonInfo ->
                            UserScreen(myUniversityInfo = university, seasonUser = seasonInfo, mainViewModel = mainViewModel) { startUserActivity() }
                        }
                    }

                }
                composable(BottomNavItem.MessageScreen.screenRoute) {
                    MainTopSwitch(mainViewModel = mainViewModel)
                    MessageScreen()
                }
                composable(BottomNavItem.CommunityScreen.screenRoute) {
                    MainTopSwitch(mainViewModel = mainViewModel)
                    CommunityScreen()
                }
                composable(ScreenNav.TierScreen.screenRoute) {
                    TierScreen(tierList = mainViewModel.tierList.value)
                }

                composable(ScreenNav.PlanetRankingScreen.screenRoute) {
                    PlanetRankingScreen(navController = navController, mainViewModel = mainViewModel)
                }
                composable(ScreenNav.SeasonRankingScreen.screenRoute) {
                    SeasonRankingScreen(navController = navController, mainViewModel = mainViewModel)
                }
                composable(ScreenNav.UniversityRankingScreen.screenRoute) {
                    UniversityRankingScreen(navController = navController, mainViewModel = mainViewModel)
                }
                composable(ScreenNav.UniversityIndividualRankingScreen.screenRoute) {
                    UniversityIndividualRankingScreen(navController = navController, mainViewModel = mainViewModel)
                }
            }
        }
    }
}