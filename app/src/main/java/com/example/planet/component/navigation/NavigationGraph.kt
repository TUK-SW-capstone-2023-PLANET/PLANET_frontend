package com.example.planet.component.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsRun
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.R
import com.example.planet.component.main.MainTopSwitch
import com.example.planet.screen.main.plogging.community.CommunityScreen
import com.example.planet.screen.main.plogging.MainScreen
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
            MainTopSwitch(mainViewModel = mainViewModel)
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.HomeScreen.screenRoute
            ) {
                composable(BottomNavItem.HomeScreen.screenRoute) {
                    MainScreen(mainViewModel = mainViewModel) { onClick() }
                }
                composable(BottomNavItem.RankingScreen.screenRoute) {
                    RankingScreen(navController = navController)
                }
                composable(BottomNavItem.UserScreen.screenRoute) {
                    UserScreen()
                }
                composable(BottomNavItem.MessageScreen.screenRoute) {
                    MessageScreen()
                }
                composable(BottomNavItem.CommunityScreen.screenRoute) {
                    CommunityScreen()
                }

                composable(ScreenNav.PlanetRankingScreen.screenRoute) {
                    PlanetRankingScreen()
                }
                composable(ScreenNav.SeasonRankingScreen.screenRoute) {
                    SeasonRankingScreen()
                }
                composable(ScreenNav.UniversityRankingScreen.screenRoute) {
                    UniversityRankingScreen()
                }
                composable(ScreenNav.UniversityIndividualRankingScreen.screenRoute) {
                    UniversityIndividualRankingScreen()
                }
            }
        }
    }
}