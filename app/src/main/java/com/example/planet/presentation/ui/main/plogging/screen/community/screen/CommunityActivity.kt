package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planet.presentation.ui.main.plogging.screen.community.navigation.CommunityNavItem
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme

class CommunityActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = intent.getStringExtra("board")
                            ?: CommunityNavItem.FreeBoardScreen.screenRoute
                    ) {
                        composable(CommunityNavItem.FreeBoardScreen.screenRoute) {
                            FreeBoardScreen(onBack = { finish() }){}
                        }
                        composable(CommunityNavItem.UniversityBoardScreen.screenRoute) {
                            UniversityBoardScreen(onBack = { finish() }){finish()}
                        }
                    }

                }
            }
        }
    }

}

