package com.example.planet.domain.usecase

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return mainRepository.getTopBanner()
    }
}