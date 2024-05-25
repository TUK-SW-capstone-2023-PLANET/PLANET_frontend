package com.example.planet.domain.usecase.chat

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatSave
import com.example.planet.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostChatUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chat: ChatSave): Flow<ApiState> {
        return chatRepository.saveChat(chat)
    }
}