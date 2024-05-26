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
fun MessageScreen(messageViewModel: MessageViewModel, userId: Long, startMessageActivity: (Long) -> Unit) {
    val titleStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )

    LaunchedEffect(Unit) {
        messageViewModel.readAllChatroom(userId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "쪽지함", style = titleStyle, modifier = Modifier.padding(start = 28.dp))
        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            SearchTextField(text = "", onValueChange = {}, fontSize = 12.sp, placeholder = "사용자 명")
        }
        LazyColumn(Modifier.fillMaxSize()) {
            items(messageViewModel.chatrooms.size) {
                CharCard(
                    name = messageViewModel.chatrooms[it].partnerUserName,
                    lastMessage = messageViewModel.chatrooms[it].content,
                    date = messageViewModel.chatrooms[it].uploadTime,
                    image = messageViewModel.chatrooms[it].partnerUserImage
                ) {
                    startMessageActivity(userId)
                }
            }
        }


        CharCard(
            name = "행복한 티노",
            lastMessage = "다음에 플로깅 같이 ㄱ",
            date = "2024.04.29 01:28",
            image = ""
        ) { }
    }
}