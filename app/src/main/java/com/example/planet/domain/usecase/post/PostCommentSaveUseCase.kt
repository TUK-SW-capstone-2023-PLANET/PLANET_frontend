package com.example.planet.domain.usecase.post

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.CommentRequest
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCommentSaveUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(commentInfo: CommentRequest): Flow<ApiState> {
        return postingRepository.postCommentSave(commentInfo)
    }
}