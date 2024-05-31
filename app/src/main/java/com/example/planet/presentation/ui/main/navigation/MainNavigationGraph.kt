package com.example.planet.presentation.ui.main.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.presentation.ui.main.plogging.component.MainTopSwitch
import com.example.planet.presentation.ui.main.plogging.navigation.PloggingNavigationGraph
import com.example.planet.presentation.ui.main.record.navigation.RecordNavigationGraph
import com.example.planet.presentation.viewmodel.CommunityViewModel
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.viewmodel.MessageViewModel

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel = viewModel(),
    communityViewModel: CommunityViewModel,
    messageViewModel: MessageViewModel,
    startMapActivity: () -> Unit,
    startUserActivity: () -> Unit,
    startCommunityActivity: (String, String) -> Unit,
    startPostedInfoActivity: (Long, String) -> Unit,
    startMyWritedActivity: (String, Long) -> Unit,
    startMessageActivity: (Long, Long, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(mainViewModel.switchState) {
            if (mainViewModel.switchState == 0) {
                navController.navigate(TopNavItem.PloggingNavigationGraph.screenRoute) {
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it) { saveState = true }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            } else if (mainViewModel.switchState == 1) {
                navController.navigate(TopNavItem.RecordNavigationGraph.screenRoute) {
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it) { saveState = true }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        MainTopSwitch(
            isChecked = mainViewModel.switchState,
            onCheckedChange = { mainViewModel.switchState = it },
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        NavHost(
            navController = navController,
            startDestination = TopNavItem.PloggingNavigationGraph.screenRoute
        ) {
            composable(TopNavItem.PloggingNavigationGraph.screenRoute) {
                PloggingNavigationGraph(
                    mainViewModel = mainViewModel,
                    communityViewModel = communityViewModel,
                    messageViewModel = messageViewModel,
                    startMapActivity = { startMapActivity() },
                    startUserActivity = { startUserActivity() },
                    startCommunityActivity = { board, universityName ->
                        startCommunityActivity(board, universityName)
                    },
                    startPostedInfoActivity = { postId, board ->
                        startPostedInfoActivity(postId, board)
                    },
                    startMyWritedActivity = { type, userId -> startMyWritedActivity(type, userId) }
                ) { userId, chatroomId, reciever ->
                    startMessageActivity(userId, chatroomId, reciever)
                }
            }
            composable(TopNavItem.RecordNavigationGraph.screenRoute) {
                RecordNavigationGraph(
                    mainViewModel = mainViewModel,
                    startMapActivity = { startMapActivity() },
                )
            }
        }
    }
}

