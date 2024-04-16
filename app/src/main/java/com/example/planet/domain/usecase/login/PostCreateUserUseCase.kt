package com.example.planet.domain.usecase.login

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.signup.PlanetUser
import com.example.planet.data.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCreateUserUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(user: PlanetUser): Flow<ApiState> {
        return loginRepository.postCreateUser(user)
    }
}