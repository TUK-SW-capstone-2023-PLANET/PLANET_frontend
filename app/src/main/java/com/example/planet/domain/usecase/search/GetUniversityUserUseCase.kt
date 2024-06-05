package com.example.planet.domain.usecase.search

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUniversityUserUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(search: String, userId: Long): Flow<ApiState> {
        return searchRepository.searchUniversityUser(search = search, userId = userId)
    }
}