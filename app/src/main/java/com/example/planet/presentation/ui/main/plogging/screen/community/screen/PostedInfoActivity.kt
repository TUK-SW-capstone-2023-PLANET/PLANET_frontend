package com.example.planet.presentation.ui.main.plogging.screen.community.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostedInfoActivity : ComponentActivity() {
    private val communityViewModel by viewModels<CommunityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        communityViewModel.currentPostId = intent.getLongExtra("postId", 1)

        lifecycleScope.launch {
            communityViewModel.readPostedInfo(intent.getLongExtra("postId", 1))
        }

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PostedInfoScreen(
                        viewModel = communityViewModel,
                        appBarTitle = intent.getStringExtra("board") ?: "자유 게시판"
                    ) { finish() }
                }
            }
        }
    }
}