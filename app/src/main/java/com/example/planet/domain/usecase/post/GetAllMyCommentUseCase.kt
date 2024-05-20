package com.example.planet.domain.usecase.post

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyCommentUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(type: String, userId: Long): Flow<ApiState> {
        return postingRepository.readAllMyComment(type = type, userId = userId)
    }
}