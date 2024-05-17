package com.example.planet.domain.usecase.post

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.PostingInfo
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostPostingSaveUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(postingInfo: PostingInfo): Flow<ApiState> {
        return postingRepository.postPostingSave(postingInfo)
    }
}