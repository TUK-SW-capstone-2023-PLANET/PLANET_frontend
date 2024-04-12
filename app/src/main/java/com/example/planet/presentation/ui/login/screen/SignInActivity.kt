package com.example.planet.presentation.ui.login.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.planet.ui.theme.MyApplicationTheme
import com.example.planet.R
import com.example.planet.presentation.ui.login.navigation.SignInNavGraph
import com.example.planet.presentation.viewmodel.SignInViewModel

class SignInActivity : ComponentActivity() {

    val signInViewModel by viewModels<SignInViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MyApplicationTheme {
                SignInNavGraph(navController = navController, signInViewModel = signInViewModel)
            }
        }
    }
}