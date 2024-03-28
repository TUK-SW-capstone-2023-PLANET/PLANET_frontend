package com.example.planet.component.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.screen.main.plogging.community.CommunityScreen
import com.example.planet.screen.main.plogging.home.MainScreen
import com.example.planet.screen.main.plogging.message.MessageScreen
import com.example.planet.screen.main.plogging.ranking.RankingScreen
import com.example.planet.screen.main.plogging.user.UserScreen
import com.example.planet.viewmodel.MainViewModel


@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel = viewModel(),
    onClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.HomeScreen.screenRoute
    ) {
        composable(BottomNavItem.HomeScreen.screenRoute) {
            MainScreen(mainViewModel = mainViewModel) { onClick() }
        }
        composable(BottomNavItem.RankingScreen.screenRoute) {
            RankingScreen()
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
    }
}