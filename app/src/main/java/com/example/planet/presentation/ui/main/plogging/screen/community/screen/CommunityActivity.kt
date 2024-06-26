package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.plogging.screen.community.navigation.CommunityNavGraph
import com.example.planet.presentation.ui.main.plogging.screen.community.navigation.CommunityNavItem
import com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost.MyWritedActivity
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.CommunityViewModel
import com.example.planet.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityActivity : ComponentActivity() {
    private val communityViewModel by viewModels<CommunityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        communityViewModel.universityName = intent.getStringExtra("university") ?: "한국공학대학교"
        communityViewModel.currentPostId

        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CommunityNavGraph(
                        viewModel = communityViewModel,
                        navController = navController,
                        startRoute = intent.getStringExtra("board")
                            ?: CommunityNavItem.FreeBoardScreen.screenRoute,
                        startMyWritedActivity = {userId: Long -> startMyWritedActivity(userId)},
                        activityFinish = { finish() }
                    ) { postId, board ->
                        startPostedInfoActivity(postId, board)
                    }
                }
            }
        }
    }

    fun startPostedInfoActivity(postId: Long, board: String) {
        val intent = Intent(this, PostedInfoActivity::class.java).apply {
            this.putExtra("postId", postId)
            this.putExtra("board", board)
        }
        startActivity(intent)
    }

    private fun startMyWritedActivity(userId: Long) {
        val intent = Intent(this, MyWritedActivity::class.java).apply {
            this.putExtra("type", "posted")
            this.putExtra("userId", userId)
        }
        startActivity(intent)
    }
}

