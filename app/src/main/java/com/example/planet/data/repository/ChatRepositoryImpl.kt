package com.example.planet.data.repository

import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatSave
import com.example.planet.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
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
}