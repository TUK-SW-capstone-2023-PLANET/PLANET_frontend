package com.example.myapplication.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.screen.MainActivity
import com.example.myapplication.screen.splash.ui.theme.PLANETTheme
import com.example.myapplication.util.RequestPermission

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PLANETTheme {
                Handler().postDelayed(Runnable {
                    // 앱의 main activity로 넘어가기
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
