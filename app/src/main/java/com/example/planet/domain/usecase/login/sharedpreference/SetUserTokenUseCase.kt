package com.example.planet.domain.usecase.login.sharedpreference

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetUserTokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(userToken: String): Flow<ApiState> {
        return userRepository.setUserPrefs(value = userToken)
    }
}