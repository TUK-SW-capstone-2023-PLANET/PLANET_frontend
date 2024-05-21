package com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.PostedInfoActivity
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.MyWritedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

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
                    when (intent.getStringExtra("type") ?: "posted") {
                        "posted" -> MyPostedScreen(
                            myWritedViewModel = myWritedViewModel,
                            title = "내가 작성한 게시글",
                            callAllAPIs = {
                                lifecycleScope.launch {
                                    listOf(
                                        async {
                                            myWritedViewModel.readAllMyPosted(
                                                type = "all",
                                                userId = myWritedViewModel.userId
                                            )
                                        },
                                        async { myWritedViewModel.readFreeMyPosted(myWritedViewModel.userId) },
                                        async { myWritedViewModel.readUniversityMyPosted(myWritedViewModel.userId) },
                                    ).awaitAll()
                                }
                            },
                            onBack = { finish() }
                        ) { postId, board -> startPostedInfoActivity(postId, board) }
                        "comment" -> MyPostedScreen(
                            myWritedViewModel = myWritedViewModel,
                            title = "내가 댓글 작성한 게시글",
                            callAllAPIs = {
                                lifecycleScope.launch {
                                    listOf(
                                        async {
                                            myWritedViewModel.readAllMyComment(
                                                type = "all",
                                                userId = myWritedViewModel.userId
                                            )
                                        },
                                        async { myWritedViewModel.readFreeMyComment(myWritedViewModel.userId) },
                                        async { myWritedViewModel.readUniversityMyComment(myWritedViewModel.userId) },
                                    ).awaitAll()
                                }
                            },
                            onBack = { finish() }
                        ) { postId, board -> startPostedInfoActivity(postId, board) }
                        "report" -> MyReportScreen(
                            myWritedViewModel = myWritedViewModel,
                            callAllAPIs = { /*TODO*/ }) {
                            finish()
                        }
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
}

