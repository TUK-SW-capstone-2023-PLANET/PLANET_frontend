package com.example.planet.domain.usecase.post

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentListReadUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(postId: Long, userId: Long): Flow<ApiState> {
        return postingRepository.getCommentRead(postId = postId, userId = userId)
    }
}