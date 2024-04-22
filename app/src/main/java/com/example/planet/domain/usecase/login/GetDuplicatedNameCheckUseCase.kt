package com.example.planet.domain.usecase.login

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDuplicatedNameCheckUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(name: String): Flow<ApiState> {
        return loginRepository.getDuplicatedNameCheck(name)
    }
}