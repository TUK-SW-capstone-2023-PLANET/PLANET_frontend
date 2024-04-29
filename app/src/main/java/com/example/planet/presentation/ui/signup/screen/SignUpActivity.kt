package com.example.planet.presentation.ui.signup.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.planet.presentation.ui.signin.screen.SignInActivity
import com.example.planet.presentation.ui.signup.navigation.SignUpNavGraph
import com.example.planet.presentation.viewmodel.SignUpViewModel
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MyApplicationTheme {
                SignUpNavGraph(navController = navController, signUpViewModel = signUpViewModel) {
                    startSignInActivity()
                }
            }
        }
    }

    private fun startSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}
