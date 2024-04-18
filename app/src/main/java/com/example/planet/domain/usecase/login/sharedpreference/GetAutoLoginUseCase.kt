package com.example.planet.domain.usecase.login.sharedpreference

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAutoLoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(autoLoginState: String = "autoLogin"): Flow<ApiState> {
        return userRepository.getAutoLoginPref(autoLoginState)
    }
}