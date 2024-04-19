package com.example.planet.domain.usecase.user

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.user.UserInfo
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutUserInfoUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userInfo: UserInfo): Flow<ApiState> {
        return userRepository.putUserInfo(userInfo)
    }
}