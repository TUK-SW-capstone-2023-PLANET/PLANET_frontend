package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatRoomId
import com.example.planet.data.remote.dto.request.chat.ChatSave
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun saveChat(chat: ChatSave): Flow<ApiState>
    suspend fun readAllChatroom(userId: Long): Flow<ApiState>
    suspend fun readAllChat(userId: Long, chatroomId: Long): Flow<ApiState>
    suspend fun deleteChatRoom(chatRoomId: ChatRoomId): Flow<ApiState>

}