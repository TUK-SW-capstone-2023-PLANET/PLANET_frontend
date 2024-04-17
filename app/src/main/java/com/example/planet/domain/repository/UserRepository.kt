package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserInfo(userId: Int): Flow<ApiState>
}