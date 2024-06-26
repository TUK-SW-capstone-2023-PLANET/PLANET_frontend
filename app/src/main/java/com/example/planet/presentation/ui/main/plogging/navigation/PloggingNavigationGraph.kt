package com.example.planet.presentation.ui.main.plogging.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planet.presentation.ui.main.plogging.component.PloggingBottomNavigation
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.CommunityScreen
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.MainScreen
import com.example.planet.presentation.ui.main.plogging.screen.home.screen.TierScreen
import com.example.planet.presentation.ui.main.plogging.screen.message.screen.MessageScreen
import com.example.planet.presentation.ui.main.plogging.screen.ranking.data.ScreenNav
import com.example.planet.presentation.ui.main.plogging.screen.ranking.screen.RankingScreen
import com.example.planet.presentation.ui.main.plogging.screen.user.screen.UserScreen
import com.example.planet.presentation.viewmodel.CommunityViewModel
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.viewmodel.MessageViewModel


@Composable
fun PloggingNavigationGraph(
    mainViewModel: MainViewModel,
    communityViewModel: CommunityViewModel,
    messageViewModel: MessageViewModel,
    startMapActivity: () -> Unit,
    startUserActivity: () -> Unit,
    startCommunityActivity: (String, String) -> Unit,
    startPostedInfoActivity: (Long, String) -> Unit,
    startMyWritedActivity: (String, Long) -> Unit,
    startMessageActivity: (Long, Long, String, Long) -> Unit,
    startRankingActivity: (String) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            PloggingBottomNavigation(navController = navController)
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
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
                        mainViewModel = mainViewModel,
                        startMapActivity = { startMapActivity() }
                    ) { startRankingActivity(it) }
                }
                composable(BottomNavItem.RankingScreen.screenRoute) {
                    RankingScreen(
                        mainViewModel = mainViewModel,
                        startRankingActivity = { startRankingActivity(it) })
                }
                composable(BottomNavItem.UserScreen.screenRoute) {
                    UserScreen(
                        mainViewModel = mainViewModel,
                        goUserActivity = { startUserActivity() }
                    ) { type, userId -> startMyWritedActivity(type, userId) }
                }
                composable(BottomNavItem.MessageScreen.screenRoute) {
                    MessageScreen(
                        messageViewModel = messageViewModel,
                        userId = mainViewModel.userId
                    ) { userId, chatroomId, reciever, recieverId ->
                        startMessageActivity(
                            userId,
                            chatroomId,
                            reciever,
                            recieverId
                        )
                    }
                }
                composable(BottomNavItem.CommunityScreen.screenRoute) {
                    CommunityScreen(
                        communityViewModel = communityViewModel,
                        startPostedInfoActivity = { postId, board ->
                            startPostedInfoActivity(
                                postId,
                                board
                            )
                        },
                    ) { board, universityName ->
                        startCommunityActivity(board, universityName)
                    }
                }
                composable(ScreenNav.TierScreen.screenRoute) {
                    TierScreen(tierList = mainViewModel.tierList.value)
                }
            }
        }

    }
}
