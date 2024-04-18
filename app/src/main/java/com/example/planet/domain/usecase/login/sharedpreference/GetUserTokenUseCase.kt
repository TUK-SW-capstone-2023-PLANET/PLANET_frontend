package com.example.planet.domain.usecase.login.sharedpreference

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(userToken: String = "userToken", defaultValue: String =""): Flow<ApiState> {
        return userRepository.getUserPref(userToken = userToken, defaultValue = defaultValue)
    }
}