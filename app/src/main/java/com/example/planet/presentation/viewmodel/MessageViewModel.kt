package com.example.planet.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatRoomId
import com.example.planet.data.remote.dto.request.chat.ChatSave
import com.example.planet.data.remote.dto.response.chat.ChatInfo
import com.example.planet.data.remote.dto.response.chat.ChatInfoResponse
import com.example.planet.data.remote.dto.response.chat.ChatResponse
import com.example.planet.data.remote.dto.response.chat.ChatroomInfo
import com.example.planet.domain.usecase.chat.DeleteChatRoomUseCase
import com.example.planet.domain.usecase.chat.GetAllChatUseCase
import com.example.planet.domain.usecase.chat.GetAllChatroomUseCase
import com.example.planet.domain.usecase.chat.PostChatUseCase
import com.example.planet.domain.usecase.login.sharedpreference.GetUserTokenUseCase
import com.example.planet.domain.usecase.search.GetChatUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val postChatUseCase: PostChatUseCase,
    private val getAllChatroomUseCase: GetAllChatroomUseCase,
    private val getAllChatUseCase: GetAllChatUseCase,
    private val deleteChatRoomUseCase: DeleteChatRoomUseCase,
    private val getChatUserUseCase: GetChatUserUseCase
) : ViewModel() {

    var userId: Long = 0
    var chatroomId: Long = 0
    var recieverId: Long = 0
    var reciever: String = ""

    var dialogState by mutableStateOf(false)
    var messageInput by mutableStateOf("")
    var chatrooms by mutableStateOf(emptyList<ChatroomInfo>())
    var chats by mutableStateOf(emptyList<ChatInfo>())

    var searchInput by mutableStateOf("")
    var searchChatroom by mutableStateOf(emptyList<ChatroomInfo>())

    init {
        viewModelScope.launch {
            getUserToken()
        }
    }


    private suspend fun getUserToken(userTokenKey: String = "userToken") {
        when (val result = getUserTokenUseCase(userTokenKey).first()) {
            is ApiState.Success<*> -> { userId = result.value as Long }

            is ApiState.Error -> {
                Log.d(TAG, "getUserToken() 실패: ${result.errMsg}")
            }

            ApiState.Loading -> TODO()
        }
    }

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

    suspend fun deleteChatRoom(onBack: () -> Unit) {
        val chatRoomId = ChatRoomId(
            userId = userId,
            chatRoomId = chatroomId
        )
        when (val apiState = deleteChatRoomUseCase(chatRoomId).first()) {
            is ApiState.Success<*> -> {
                if ((apiState.value as ChatResponse).message == "채팅방 삭제 성공") {
                    onBack()
                }
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "deleteChatRoom() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }

    suspend fun searchChatUser() {
        when (val apiState = getChatUserUseCase(userId, searchInput).first()) {
            is ApiState.Success<*> -> {
                searchChatroom = apiState.value as List<ChatroomInfo>
            }
            is ApiState.Error -> {
                Log.d("daeYoung", "searchChatUser() 실패: ${apiState.errMsg}")
            }
            ApiState.Loading -> TODO()
        }
    }






}