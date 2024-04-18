package com.example.planet.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.planet.presentation.ui.main.plogging.screen.MainActivity
import com.example.planet.presentation.ui.signin.screen.SignInActivity
import com.example.planet.presentation.ui.splash.ui.theme.PLANETTheme
import com.example.planet.presentation.viewmodel.SignInViewModel
import com.example.planet.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PLANETTheme {
                Handler().postDelayed(Runnable {
                    splashViewModel.goActivity(
                        listOf({ startMainActivity() }, { startSignInActivity() })
                    )
                }, 2000)
                SplashScreen()
            }
        }
    }

    private fun startSignInActivity() {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun startMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}
