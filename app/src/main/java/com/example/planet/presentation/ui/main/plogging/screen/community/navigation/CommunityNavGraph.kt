package com.example.planet.presentation.ui.main.plogging.screen.community.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.FreeBoardScreen
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.PostingScreen
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.UniversityBoardScreen
import com.example.planet.presentation.viewmodel.CommunityViewModel

@Composable
fun CommunityNavGraph(
    viewModel: CommunityViewModel,
    navController: NavHostController,
    startRoute: String,
    activityFinish: () -> Unit,
    startPostedInfoActivity: (Long, String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable(CommunityNavItem.FreeBoardScreen.screenRoute) {
            FreeBoardScreen(
                viewModel = viewModel,
                onPosting = { type -> navController.navigate("${CommunityNavItem.PostingScreen.screenRoute}/${type}") },
                onBack = { activityFinish() },
                onSearch = {}) { postId, board ->
                startPostedInfoActivity(postId, board)
            }
        }
        composable(CommunityNavItem.UniversityBoardScreen.screenRoute) {
            UniversityBoardScreen(
                viewModel = viewModel,
                onPosting = { type -> navController.navigate("${CommunityNavItem.PostingScreen.screenRoute}/${type}") },
                onBack = { activityFinish() },
                onSearch = {}) { postId, board ->
                startPostedInfoActivity(postId, board)
            }
        }
        composable("${CommunityNavItem.PostingScreen.screenRoute}/{type}") {
            PostingScreen(viewModel = viewModel, types = it.arguments?.getString("type") ?: "free", onBack = { activityFinish() })
        }
    }
}