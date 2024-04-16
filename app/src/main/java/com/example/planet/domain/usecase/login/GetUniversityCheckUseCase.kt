package com.example.planet.domain.usecase.login

import com.example.planet.data.ApiState
import com.example.planet.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUniversityCheckUseCase @Inject constructor(val loginRepository: LoginRepository) {
    suspend operator fun invoke(university: String): Flow<ApiState> {
        return loginRepository.getUniversityCheck(university)
    }
}