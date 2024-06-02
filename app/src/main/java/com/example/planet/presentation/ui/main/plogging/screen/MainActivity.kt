package com.example.planet.presentation.ui.main.plogging.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.planet.TAG
import com.example.planet.presentation.ui.main.navigation.MainNavigationGraph
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.CommunityActivity
import com.example.planet.presentation.ui.main.plogging.screen.community.screen.PostedInfoActivity
import com.example.planet.presentation.ui.main.plogging.screen.message.screen.MessageActivity
import com.example.planet.presentation.ui.main.plogging.screen.user.screen.UserActivity
import com.example.planet.presentation.ui.main.plogging.screen.user.screen.mypost.MyWritedActivity
import com.example.planet.presentation.ui.plogging.screen.MapActivity
import com.example.planet.presentation.ui.plogging.screen.PloggingResultActivity
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.CommunityViewModel
import com.example.planet.presentation.viewmodel.MainViewModel
import com.example.planet.presentation.viewmodel.MessageViewModel
import com.example.planet.presentation.viewmodel.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val mainViewModel by viewModels<MainViewModel>()
    private val communityViewModel by viewModels<CommunityViewModel>()
    private val messageViewModel by viewModels<MessageViewModel>()
    private val recordViewModel by viewModels<RecordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            mainViewModel
            MyApplicationTheme {
//                RequestPermission()
                MainNavigationGraph(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    communityViewModel = communityViewModel,
                    messageViewModel = messageViewModel,
                    recordViewModel = recordViewModel,
                    startMapActivity = { startMapActivity() },
                    startUserActivity = { startUserActivity() },
                    startCommunityActivity = { board, universityName ->
                        startCommunityActivity(board, universityName)
                    },
                    startPostedInfoActivity = { postId, board ->
                        startPostedInfoActivity(postId, board)
                    },
                    startMyWritedActivity = { type, userId -> startMyWritedActivity(type, userId) },
                    startMessageActivity = { userId, chatroomId, reciever ->
                        startMessageActivity(userId, chatroomId, reciever)
                    },
                    startPloggingResultActivity = {startPloggingResultActivity(it)}
                )
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume()")

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity onPause()")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity onStop()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity onCreate()")

    }


    private fun startMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    private fun startUserActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent)
    }

    private fun startCommunityActivity(bulletinBoard: String, universityName: String) {
        val intent = Intent(this, CommunityActivity::class.java).apply {
            this.putExtra("board", bulletinBoard)
            this.putExtra("university", universityName)
        }
        startActivity(intent)
    }

    private fun startPostedInfoActivity(postId: Long, board: String) {
        val intent = Intent(this, PostedInfoActivity::class.java).apply {
            this.putExtra("postId", postId)
            this.putExtra("board", board)
        }
        startActivity(intent)
    }

    private fun startMyWritedActivity(type: String, userId: Long) {
        val intent = Intent(this, MyWritedActivity::class.java).apply {
            this.putExtra("type", type)
            this.putExtra("userId", userId)
        }
        startActivity(intent)
    }

    private fun startMessageActivity(userId: Long, chatroomId: Long, reciever: String) {
        val intent = Intent(this, MessageActivity::class.java).apply {
            this.putExtra("userId", userId)
            this.putExtra("chatroomId", chatroomId)
            this.putExtra("reciever", reciever)
        }
        startActivity(intent)
    }

    private fun startPloggingResultActivity(ploggingId: Long) {
        val intent = Intent(this, PloggingResultActivity::class.java).also {
            it.putExtra("ploggingId", ploggingId)
        }
        startActivity(intent)
    }
}



