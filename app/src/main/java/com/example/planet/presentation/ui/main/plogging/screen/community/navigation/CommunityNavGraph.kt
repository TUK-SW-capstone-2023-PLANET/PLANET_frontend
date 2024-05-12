package com.example.planet.presentation.ui.main.plogging.screen.community.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.FreeBoardScreen
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.PostedInfoScreen
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.UniversityBoardScreen

@Composable
fun CommunityNavGraph(navController: NavHostController, startRoute: String, activityFinish: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(CommunityNavItem.FreeBoardScreen.screenRoute) {
            FreeBoardScreen(navController = navController, onBack = { activityFinish() }) {}
        }
        composable(CommunityNavItem.UniversityBoardScreen.screenRoute) {
            UniversityBoardScreen(navController = navController, onBack = { activityFinish() }) { activityFinish() }
        }
        composable("${CommunityNavItem.PostedInfoScreen.screenRoute}/{title}") {
            PostedInfoScreen(navController = navController, appBarTitle = it.arguments?.getString("title") ?: "자유 게시판")
        }
    }
}