package com.example.planet.presentation.ui.signup.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.planet.R
import com.example.planet.presentation.ui.signup.component.TopIndicator
import com.example.planet.presentation.ui.signup.screen.AuthScreen
import com.example.planet.presentation.ui.signup.screen.NameScreen
import com.example.planet.presentation.viewmodel.SignUpViewModel
import com.example.planet.presentation.viewmodel.navRouteList

@Composable
fun SignUpNavGraph(navController: NavHostController, signUpViewModel: SignUpViewModel, goBack: () -> Unit) {

    BackHandler {
        if(signUpViewModel.currentPage.value == 1) {
            goBack()
        } else {
            signUpViewModel.onPreviousPage(navController)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(23.dp)
                    .align(Alignment.Start),
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
                    NameScreen()
                }
                composable(SignUpNavItem.PasswdScreen.screenRoute) {
                }
                composable(SignUpNavItem.HeightScreen.screenRoute) {
                }
                composable(SignUpNavItem.FinalScreen.screenRoute) {
                }
            }
        }
    }
}