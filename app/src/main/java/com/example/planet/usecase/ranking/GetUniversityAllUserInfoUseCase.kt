package com.example.planet.usecase.ranking

import com.example.planet.data.ApiState
import com.example.planet.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUniversityAllUserInfoUseCase @Inject constructor(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return mainRepository.getUniversityAllUserInfo()
    }
}