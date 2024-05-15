package com.example.planet.presentation.ui.main.plogging.screen.community.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.FreeBoardScreen
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.PostedInfoScreen
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.PostingScreen
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.UniversityBoardScreen
import com.example.planet.presentation.viewmodel.CommunityViewModel

@Composable
fun CommunityNavGraph(viewModel: CommunityViewModel, navController: NavHostController, startRoute: String, activityFinish: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(CommunityNavItem.FreeBoardScreen.screenRoute) {
            FreeBoardScreen(viewModel = viewModel, navController = navController, onBack = { activityFinish() }) {}
        }
        composable(CommunityNavItem.UniversityBoardScreen.screenRoute) {
            UniversityBoardScreen(viewModel = viewModel, navController = navController, onBack = { activityFinish() }) { activityFinish() }
        }
        composable("${CommunityNavItem.PostedInfoScreen.screenRoute}/{title}") {
            PostedInfoScreen(viewModel = viewModel, navController = navController, appBarTitle = it.arguments?.getString("title") ?: "자유 게시판")
        }
        composable(CommunityNavItem.PostingScreen.screenRoute) {
            PostingScreen(viewModel = viewModel, navController = navController)
        }
    }
}