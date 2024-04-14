package com.example.planet.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.planet.presentation.ui.signin.screen.SignInActivity
import com.example.planet.presentation.ui.splash.ui.theme.PLANETTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PLANETTheme {
                Handler().postDelayed(Runnable {
                    // 앱의 main activity로 넘어가기
//                    val i = Intent(this, MainActivity::class.java)
                    val i = Intent(this, SignInActivity::class.java)
                    startActivity(i)
                    // 현재 액티비티 닫기
                    finish()
                }, 2000)
                SplashScreen()
            }
        }
    }
}
