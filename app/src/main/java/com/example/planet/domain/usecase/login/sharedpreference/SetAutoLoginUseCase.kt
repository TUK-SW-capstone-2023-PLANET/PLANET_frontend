package com.example.planet.domain.usecase.login.sharedpreference

import android.util.Log
import com.example.planet.TAG
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetAutoLoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(autoLoginState: Boolean = false): Flow<ApiState> {
        return userRepository.setAutoLoginPref(value = autoLoginState)
    }
}