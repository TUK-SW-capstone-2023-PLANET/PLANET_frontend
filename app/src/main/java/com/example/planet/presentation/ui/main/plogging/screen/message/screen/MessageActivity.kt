package com.example.planet.presentation.ui.main.plogging.screen.message.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.planet.presentation.ui.main.plogging.screen.message.navigation.MessageNavGraph
import com.example.planet.presentation.ui.main.plogging.screen.message.navigation.MessageNavItem
import com.example.planet.presentation.ui.ui.theme.MyApplicationTheme
import com.example.planet.presentation.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageActivity : ComponentActivity() {

    val messageViewModel by viewModels<MessageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        messageViewModel.userId = intent.getLongExtra("userId", 0)
        messageViewModel.chatroomId = intent.getLongExtra("chatroomId", 0)
        messageViewModel.reciever = intent.getStringExtra("reciever") ?: "알 수 없음"
        messageViewModel.recieverId = intent.getLongExtra("recieverId",0)

        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MessageNavGraph(
                        messageViewModel = messageViewModel,
                        navController = navController,
                        startRoute = intent.getStringExtra("startRoute")
                            ?: MessageNavItem.MessageLogScreen.screenRoute
                    ) {
                        finish()
                    }
                }
            }
        }
    }
}

