package com.example.planet.domain.usecase.chat

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatRoomId
import com.example.planet.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteChatRoomUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatRoomId: ChatRoomId): Flow<ApiState> {
        return chatRepository.deleteChatRoom(chatRoomId)
    }
}