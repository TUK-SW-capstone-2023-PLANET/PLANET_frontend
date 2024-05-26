package com.example.planet.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatSave
import com.example.planet.data.remote.dto.response.chat.ChatInfo
import com.example.planet.data.remote.dto.response.chat.ChatInfoResponse
import com.example.planet.data.remote.dto.response.chat.ChatroomInfo
import com.example.planet.data.remote.dto.response.chat.ChatResponse
import com.example.planet.domain.usecase.chat.GetAllChatUseCase
import com.example.planet.domain.usecase.chat.GetAllChatroomUseCase
import com.example.planet.domain.usecase.chat.PostChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postChatUseCase: PostChatUseCase,
    private val getAllChatroomUseCase: GetAllChatroomUseCase,
    private val getAllChatUseCase: GetAllChatUseCase
) : ViewModel() {

    var userId: Long = 0
    var chatroomId: Long = 0
    var reciever: String = ""

    var dialogState by mutableStateOf(false)
    var messageInput by mutableStateOf("")
    var chatrooms by mutableStateOf(emptyList<ChatroomInfo>())
    var chats by mutableStateOf(emptyList<ChatInfo>())


    suspend fun saveChat(content: String, receiverId: Long, onBack: () -> Unit) {
        val chat = ChatSave(
            senderId = userId,
            receiverId = receiverId,
            content = content
        )
        when (val apiState = postChatUseCase(chat).first()) {
            is ApiState.Success<*> -> {
                if ((apiState.value as ChatResponse).message == "쪽지 저장 성공") {
                    onBack()
                    messageInput = ""
                    Toast.makeText(context, "쪽지 저장 성공", Toast.LENGTH_SHORT).show()
                }
            }

            is ApiState.Error -> {
                Log.d("daeYoung", "saveChat() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readAllChatroom(userId: Long) {
        when (val apiState = getAllChatroomUseCase(userId).first()) {
            is ApiState.Success<*> -> {
                chatrooms = (apiState.value as List<ChatroomInfo>)
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readAllChatroom() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

    suspend fun readAllChat() {
        when (val apiState = getAllChatUseCase(chatroomId = chatroomId, userId = userId).first()) {
            is ApiState.Success<*> -> {
                if ((apiState.value as ChatInfoResponse).message == "쪽지 전체 조회 성공") {
                    chats = apiState.value.chats
                }
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "readAllChat() 실패: ${apiState.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }


}