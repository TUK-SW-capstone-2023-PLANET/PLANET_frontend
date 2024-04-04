package com.example.planet.usecase

import com.example.planet.data.ApiState
import com.example.planet.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GerTierListUseCase @Inject constructor(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return mainRepository.getTierList()
    }
}