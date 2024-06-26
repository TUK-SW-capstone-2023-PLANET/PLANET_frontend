package com.example.planet.data.repository

import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatRoomId
import com.example.planet.data.remote.dto.request.chat.ChatSave
import com.example.planet.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val mainApi: MainApi): ChatRepository {
    override suspend fun saveChat(chat: ChatSave) = flow {
        kotlin.runCatching {
            mainApi.postChat(chat)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readAllChatroom(userId: Long) = flow {
        kotlin.runCatching {
            mainApi.getAllChatroom(userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readAllChat(userId: Long, chatroomId: Long) = flow {
        kotlin.runCatching {
            mainApi.getAllChat(chatRoomId = chatroomId, userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteChatRoom(chatRoomId: ChatRoomId) = flow {
        kotlin.runCatching {
            mainApi.deleteChatRoom(chatRoomId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}