package com.example.planet.domain.usecase.plogging

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostPloggingUseCase @Inject constructor(private val ploggingRepository: PloggingRepository) {
    suspend operator fun invoke(ploggingInfo: PloggingInfo): Flow<ApiState> {
        return ploggingRepository.postPlogging(ploggingInfo = ploggingInfo)
    }
}