package com.example.planet.presentation.ui.main.plogging.screen.message.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planet.presentation.ui.component.SearchTextField
import com.example.planet.presentation.ui.main.plogging.screen.message.component.CharCard
import com.example.planet.presentation.viewmodel.MessageViewModel

@Composable
fun MessageScreen(
    messageViewModel: MessageViewModel,
    userId: Long,
    startMessageActivity: (Long, Long, String, Long) -> Unit
) {
    val titleStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )

    LaunchedEffect(Unit) {
        messageViewModel.readAllChatroom(userId)
    }

    LaunchedEffect(messageViewModel.searchInput) {
        if (messageViewModel.searchInput.isEmpty()){
            messageViewModel.searchChatroom = emptyList()
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "쪽지함", style = titleStyle, modifier = Modifier.padding(start = 28.dp))
        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            SearchTextField(
                text = { messageViewModel.searchInput },
                onValueChange = { messageViewModel.searchInput = it },
                fontSize = 12.sp,
                placeholder = "사용자 명"
            ) {
                messageViewModel.searchChatUser()
            }
        }

        LazyColumn(Modifier.fillMaxSize()) {
            if (messageViewModel.searchInput.isEmpty() && messageViewModel.searchChatroom.isEmpty()) {
                items(messageViewModel.chatrooms.size) {
                    CharCard(
                        name = messageViewModel.chatrooms[it].partnerUserName,
                        lastMessage = messageViewModel.chatrooms[it].content,
                        date = messageViewModel.chatrooms[it].uploadTime,
                        image = messageViewModel.chatrooms[it].partnerUserImage
                    ) {
                        startMessageActivity(
                            userId,
                            messageViewModel.chatrooms[it].chatRoomId,
                            messageViewModel.chatrooms[it].partnerUserName,
                            messageViewModel.chatrooms[it].partnerUserId
                        )
                    }
                }
            } else {
                items(messageViewModel.searchChatroom.size) {
                    CharCard(
                        name = messageViewModel.searchChatroom[it].partnerUserName,
                        lastMessage = messageViewModel.searchChatroom[it].content,
                        date = messageViewModel.searchChatroom[it].uploadTime,
                        image = messageViewModel.searchChatroom[it].partnerUserImage
                    ) {
                        startMessageActivity(
                            userId,
                            messageViewModel.searchChatroom[it].chatRoomId,
                            messageViewModel.searchChatroom[it].partnerUserName,
                            messageViewModel.searchChatroom[it].partnerUserId
                        )
                    }
                }
            }
        }
    }
}