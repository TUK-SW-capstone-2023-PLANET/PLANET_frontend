package com.example.planet.presentation.ui.signup.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.planet.presentation.ui.map.MapActivity
import com.example.planet.presentation.ui.signup.navigation.SignUpNavGraph
import com.example.planet.presentation.viewmodel.SignUpViewModel
import com.example.planet.ui.theme.MyApplicationTheme

class SignUpActivity : ComponentActivity() {
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MyApplicationTheme {
                SignUpNavGraph(navController = navController, signUpViewModel = signUpViewModel) {
                    startSignUpActivity()
                }
            }
        }
    }

    private fun startSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}
