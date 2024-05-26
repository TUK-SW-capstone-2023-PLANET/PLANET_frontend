package com.example.planet.domain.usecase.chat

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllChatUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatroomId: Long, userId: Long): Flow<ApiState> {
        return chatRepository.readAllChat(chatroomId = chatroomId, userId = userId)
    }
}