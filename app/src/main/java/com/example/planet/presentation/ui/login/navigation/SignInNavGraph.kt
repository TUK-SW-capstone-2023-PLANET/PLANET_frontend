package com.example.planet.presentation.ui.login.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.presentation.ui.login.screen.SignInScreen
import com.example.planet.presentation.viewmodel.SignInViewModel

@Composable
fun SignInNavGraph(navController: NavHostController, signInViewModel: SignInViewModel) {
    NavHost(
        navController = navController,
        startDestination = SignInNavItem.SignInScreen.screenRoute
    ) {
        composable(SignInNavItem.SignInScreen.screenRoute) {
            SignInScreen(signInViewModel = signInViewModel, navController = navController)
        }
        composable(SignInNavItem.AuthScreen.screenRoute) {
        }
        composable(SignInNavItem.NameScreen.screenRoute) {
        }
        composable(SignInNavItem.PasswdScreen.screenRoute) {
        }
        composable(SignInNavItem.HeightScreen.screenRoute) {
        }
        composable(SignInNavItem.FinalScreen.screenRoute) {
        }
    }
}