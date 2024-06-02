package com.example.planet.domain.usecase.plogging

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPloggingInfoUseCase @Inject constructor(private val ploggingRepository: PloggingRepository) {
    suspend operator fun invoke(ploggingId: Long): Flow<ApiState> {
        return ploggingRepository.getPloggingInfo(ploggingId)
    }
}