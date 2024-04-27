package com.example.planet.presentation.ui.signup.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.R
import com.example.planet.presentation.ui.signup.component.TopIndicator
import com.example.planet.presentation.ui.signup.screen.AuthScreen
import com.example.planet.presentation.ui.signup.screen.FinalScreen
import com.example.planet.presentation.ui.signup.screen.MyInfoScreen
import com.example.planet.presentation.ui.signup.screen.NameScreen
import com.example.planet.presentation.ui.signup.screen.PasswdScreen
import com.example.planet.presentation.viewmodel.SignUpViewModel
import com.example.planet.presentation.viewmodel.navRouteList
import com.example.planet.presentation.util.noRippleClickable

@Composable
fun SignUpNavGraph(
    navController: NavHostController,
    signUpViewModel: SignUpViewModel,
    goLoginActivity: () -> Unit
) {

    BackHandler {
        if (signUpViewModel.currentPage.value == 1) {
            goLoginActivity()
        } else {
            signUpViewModel.onPreviousPage(navController)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Start)
                    .noRippleClickable {
                        if (signUpViewModel.currentPage.value == 1) goLoginActivity()
                        else {
                            signUpViewModel.onPreviousPage(navController)
                        }
                    },
                tint = colorResource(id = R.color.font_background_color1)
            )
            Spacer(modifier = Modifier.height(14.5.dp))

            TopIndicator(page = navRouteList.size, currentPage = signUpViewModel.currentPage.value)
            Spacer(modifier = Modifier.height(30.dp))

            NavHost(
                navController = navController,
                startDestination = SignUpNavItem.AuthScreen.screenRoute
            ) {
                composable(SignUpNavItem.AuthScreen.screenRoute) {
                    AuthScreen(navController = navController, signUpViewModel = signUpViewModel)
                }
                composable(SignUpNavItem.NameScreen.screenRoute) {
                    NameScreen(navController = navController, signUpViewModel = signUpViewModel)
                }
                composable(SignUpNavItem.PasswdScreen.screenRoute) {
                    PasswdScreen(navController = navController, signUpViewModel = signUpViewModel)
                }
                composable(SignUpNavItem.MyInfoScreen.screenRoute) {
                    MyInfoScreen(navController = navController, signUpViewModel = signUpViewModel)
                }
                composable(SignUpNavItem.FinalScreen.screenRoute) {
                    FinalScreen { goLoginActivity() }
                }
            }
        }
    }
}