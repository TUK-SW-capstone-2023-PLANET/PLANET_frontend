package com.example.planet.domain.usecase.user

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId: Long): Flow<ApiState> {
        return userRepository.getUserInfo(userId.toInt())
    }
}