package com.example.planet.domain.usecase.plogging

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPloggingIdUseCase @Inject constructor(private val ploggingRepository: PloggingRepository) {
    suspend operator fun invoke(userId: Long): Flow<ApiState> {
        return ploggingRepository.getPloggingId(userId)
    }
}