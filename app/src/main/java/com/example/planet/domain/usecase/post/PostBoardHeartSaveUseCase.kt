package com.example.planet.domain.usecase.post

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostBoardHeartSaveUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(postId: PostId): Flow<ApiState> {
        return postingRepository.postBoardHeartSave(postId)
    }
}