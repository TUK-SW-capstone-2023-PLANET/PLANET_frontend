package com.example.planet.domain.usecase.board

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHotPostedUseCase @Inject constructor(private val postingRepository: PostingRepository) {
    suspend operator fun invoke(type: String): Flow<ApiState> {
        return postingRepository.getHotPosted(type)
    }
}