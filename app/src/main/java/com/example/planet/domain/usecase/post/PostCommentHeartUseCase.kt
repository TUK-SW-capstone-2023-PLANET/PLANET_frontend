package com.example.planet.domain.usecase.post

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.CommentId
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCommentHeartUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(commentId: CommentId): Flow<ApiState> {
        return postingRepository.postCommentHeart(commentId)
    }
}