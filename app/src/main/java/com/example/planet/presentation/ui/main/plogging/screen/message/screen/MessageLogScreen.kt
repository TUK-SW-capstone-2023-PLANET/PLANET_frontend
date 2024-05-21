package com.example.planet.presentation.ui.main.plogging.screen.message.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planet.presentation.ui.main.plogging.screen.message.component.MessageDialog
import com.example.planet.presentation.ui.main.plogging.screen.message.component.MessageLogCard
import com.example.planet.presentation.ui.main.plogging.screen.message.component.MessageTopAppBar
import com.example.planet.presentation.viewmodel.MessageViewModel


@Composable
fun MessageLogScreen(
    messageViewModel: MessageViewModel,
    onBack: () -> Unit,
    goSendScreen: () -> Unit
) {

    if (messageViewModel.dialogState == true) {
        MessageDialog(
            title = "정말 채팅방을 나가시겠습니까?",
            content1 = "아니요",
            content2 = "예",
            icon1 = Icons.Default.Close,
            icon2 = Icons.Default.Check,
            onClick = { /*TODO*/ },
            onClose = { messageViewModel.dialogState = it }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        MessageTopAppBar(
            title = "행복한 티노",
            onBack = { onBack() },
            goSendScreen = { goSendScreen() }) {
            messageViewModel.dialogState = true
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(5) {
                MessageLogCard(type = "받은 쪽지", date = "2024.04.29 01:28", content = "오늘 플로깅 재미있었다.")
            }
            items(5) {
                MessageLogCard(type = "보낸 쪽지", date = "2024.04.29 01:28", content = "오늘 플로깅 재미있었다.")
            }
        }
    }
}