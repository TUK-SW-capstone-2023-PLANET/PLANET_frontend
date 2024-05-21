package com.example.planet.presentation.ui.main.plogging.screen.message.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.presentation.ui.main.plogging.screen.message.screen.MessageLogScreen
import com.example.planet.presentation.ui.main.plogging.screen.message.screen.SendMessageScreen
import com.example.planet.presentation.viewmodel.MessageViewModel

@Composable
fun MessageNavGraph(
    messageViewModel: MessageViewModel,
    navController: NavHostController,
    onBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = MessageNavItem.MessageLogScreen.screenRoute
    ) {
        composable(MessageNavItem.MessageLogScreen.screenRoute) {
            MessageLogScreen(messageViewModel = messageViewModel, onBack = { onBack() }) {
                navController.navigate(MessageNavItem.SendMessageScreen.screenRoute)
            }
        }
        composable(MessageNavItem.SendMessageScreen.screenRoute) {
            SendMessageScreen(
                messageViewModel = messageViewModel,
                onBack = { navController.popBackStack() })
        }
    }
}