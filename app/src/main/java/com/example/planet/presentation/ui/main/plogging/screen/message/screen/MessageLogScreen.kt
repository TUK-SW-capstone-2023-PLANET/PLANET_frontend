package com.example.planet.presentation.ui.main.plogging.screen.message.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.planet.presentation.ui.main.plogging.screen.message.component.MessageDialog
import com.example.planet.presentation.ui.main.plogging.screen.message.component.MessageLogCard
import com.example.planet.presentation.ui.main.plogging.screen.message.component.MessageTopAppBar
import com.example.planet.presentation.viewmodel.MessageViewModel
import kotlinx.coroutines.launch


@Composable
fun MessageLogScreen(
    messageViewModel: MessageViewModel,
    onBack: () -> Unit,
    goSendScreen: () -> Unit
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        messageViewModel.readAllChat()
    }

    if (messageViewModel.dialogState == true) {
        MessageDialog(
            title = "정말 채팅방을 나가시겠습니까?",
            content1 = "아니요",
            content2 = "예",
            icon1 = Icons.Default.Close,
            icon2 = Icons.Default.Check,
            onClick = { scope.launch{ messageViewModel.deleteChatRoom{onBack()} } },
            onClose = { messageViewModel.dialogState = it }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        MessageTopAppBar(
            title = messageViewModel.reciever,
            onBack = { onBack() },
            goSendScreen = { goSendScreen() }) {
            messageViewModel.dialogState = true
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(messageViewModel.chats.size) {
                MessageLogCard(
                    type = messageViewModel.chats[it].type,
                    date = messageViewModel.chats[it].uploadTime,
                    content = messageViewModel.chats[it].content
                )
            }
        }
    }
}