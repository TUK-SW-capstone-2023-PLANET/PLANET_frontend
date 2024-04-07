package com.example.planet.component.navigation

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
import com.example.planet.screen.main.plogging.community.CommunityScreen
import com.example.planet.screen.main.plogging.home.MainScreen
import com.example.planet.screen.main.plogging.home.TierScreen
import com.example.planet.screen.main.plogging.message.MessageScreen
import com.example.planet.screen.main.plogging.ranking.PlanetRankingScreen
import com.example.planet.screen.main.plogging.ranking.RankingScreen
import com.example.planet.screen.main.plogging.ranking.SeasonRankingScreen
import com.example.planet.screen.main.plogging.ranking.UniversityIndividualRankingScreen
import com.example.planet.screen.main.plogging.ranking.UniversityRankingScreen
import com.example.planet.screen.main.plogging.user.UserScreen
import com.example.planet.viewmodel.MainViewModel


@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel = viewModel(),
    onClick: () -> Unit
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
                    MainScreen(navController = navController, mainViewModel = mainViewModel) { onClick() }
                }
                composable(BottomNavItem.RankingScreen.screenRoute) {
                    RankingScreen(navController = navController, mainViewModel = mainViewModel)
                }
                composable(BottomNavItem.UserScreen.screenRoute) {
                    MainTopSwitch(mainViewModel = mainViewModel)
                    UserScreen()
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