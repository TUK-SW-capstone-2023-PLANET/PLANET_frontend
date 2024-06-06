package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchPlanet(search: String): Flow<ApiState>
    suspend fun searchUniversity(search: String): Flow<ApiState>
    suspend fun searchUniversityUser(search: String, userId: Long): Flow<ApiState>
    suspend fun searchSeason(search: String): Flow<ApiState>
    suspend fun readRecentlyWord(userId: Long): Flow<ApiState>
    suspend fun searchPosted(type: String, userId: Long, search: String): Flow<ApiState>
    suspend fun searchChat(userId: Long, search: String): Flow<ApiState>
    suspend fun readRecentlyMapWord(userId: Long): Flow<ApiState>
    suspend fun searchPlace(userId: Long, search: String): Flow<ApiState>
}