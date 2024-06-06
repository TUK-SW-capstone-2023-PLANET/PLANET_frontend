package com.example.planet.domain.usecase.search

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatUserUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(userId: Long, search: String): Flow<ApiState> {
        return searchRepository.searchChat(userId = userId, search = search)
    }
}