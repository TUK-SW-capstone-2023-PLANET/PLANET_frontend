package com.example.planet.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.planet.presentation.ui.main.plogging.screen.MainActivity
import com.example.planet.presentation.ui.splash.ui.theme.PLANETTheme
import com.example.planet.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PLANETTheme {
                Handler().postDelayed(Runnable {
                    // 앱의 main activity로 넘어가기
//                    val i = Intent(this, MainActivity::class.java)
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    // 현재 액티비티 닫기
                    finish()
                }, 2000)
                SplashScreen()
            }
        }
    }
}
