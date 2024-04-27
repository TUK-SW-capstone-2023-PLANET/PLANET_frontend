package com.example.planet.presentation.ui.signin.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.plogging.screen.MainActivity
import com.example.planet.presentation.ui.signup.screen.SignUpActivity
import com.example.planet.presentation.viewmodel.SignInViewModel
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : ComponentActivity() {
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d(TAG, "SignInActivity 실행")

            MyApplicationTheme {
                SignInScreen(signInViewModel = signInViewModel, startSignUpActivity = { startSignUpActivity() }) { startMainActivity() }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}