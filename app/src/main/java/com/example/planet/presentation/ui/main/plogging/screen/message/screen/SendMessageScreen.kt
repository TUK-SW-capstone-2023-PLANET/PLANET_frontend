package com.example.planet.presentation.ui.main.plogging.screen.message.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.planet.R
import com.example.planet.presentation.ui.main.plogging.screen.message.component.SendMessageTopAppBar
import com.example.planet.presentation.viewmodel.MessageViewModel
import kotlinx.coroutines.launch

@Composable
fun SendMessageScreen(messageViewModel: MessageViewModel, onBack: () -> Unit) {

    val scope = rememberCoroutineScope()
    val placeholderStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(id = R.color.font_background_color2)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        SendMessageTopAppBar(title = "쪽지 보내기", onBack = { onBack() }) {
            scope.launch {
                messageViewModel.saveChat(
                    receiverId = messageViewModel.recieverId,
                    content = messageViewModel.messageInput
                ) { onBack() }
            }
        }
        TextField(
            value = messageViewModel.messageInput,
            onValueChange = { messageViewModel.messageInput = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "내용을 입력하세요,", style = placeholderStyle) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }

}