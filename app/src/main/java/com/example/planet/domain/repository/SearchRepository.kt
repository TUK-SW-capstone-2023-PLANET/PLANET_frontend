package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.chat.ChatSave
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchPlanet(search: String): Flow<ApiState>
    suspend fun searchUniversity(search: String): Flow<ApiState>
    suspend fun searchUniversityUser(search: String, userId: Long): Flow<ApiState>
    suspend fun searchSeason(search: String): Flow<ApiState>
    suspend fun searchRecentlyWord(userId: Long): Flow<ApiState>
    suspend fun searchPosted(type: String, userId: Long, search: String): Flow<ApiState>
    suspend fun searchChat(userId: Long, search: String): Flow<ApiState>


}