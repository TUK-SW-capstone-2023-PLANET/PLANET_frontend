package com.example.planet.domain.usecase.post

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPostedUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(type: String, userId: Long): Flow<ApiState> {
        return postingRepository.readAllMyPosted(type = type, userId = userId)
    }
}