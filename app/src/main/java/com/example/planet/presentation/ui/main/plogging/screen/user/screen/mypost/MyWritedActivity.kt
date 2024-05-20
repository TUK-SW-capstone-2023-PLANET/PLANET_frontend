package com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.MyWritedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyWritedActivity : ComponentActivity() {

    private val myWritedViewModel by viewModels<MyWritedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myWritedViewModel.userId = intent.getLongExtra("userId", 0L)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when(intent.getStringExtra("type") ?: "") {
                        "posted" -> MyPostedScreen(myWritedViewModel = myWritedViewModel) { finish() }
                    }
                }
            }
        }
    }
}

