package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planet.presentation.ui.main.plogging.screen.community.component.CommunityTopAppBar
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme

class CommunityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FreeBoardScreen()
                }
            }
        }
    }
}

@Composable
fun FreeBoardScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        CommunityTopAppBar(title = "자유게시판", onBack = { /*TODO*/ }) {

        }
    }

}