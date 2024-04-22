package com.example.planet.domain.usecase.login

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthCodeCheckUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(code: String, university: String, email: String): Flow<ApiState> {
        return loginRepository.getAuthCodeCheck(code = code, name = university, email = email)
    }
}