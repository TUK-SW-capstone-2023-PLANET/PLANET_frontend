package com.example.planet.domain.usecase.login

import com.example.planet.data.ApiState
import com.example.planet.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthCodeUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(university: String, email: String): Flow<ApiState> {
        return loginRepository.getAuthCode(name = university, email = email)
    }
}