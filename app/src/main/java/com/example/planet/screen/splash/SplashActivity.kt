package com.example.planet.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import com.example.planet.TAG
import com.example.planet.screen.main.MainActivity
import com.example.planet.screen.map.MapActivity
import com.example.planet.screen.splash.ui.theme.PLANETTheme
import com.example.planet.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
