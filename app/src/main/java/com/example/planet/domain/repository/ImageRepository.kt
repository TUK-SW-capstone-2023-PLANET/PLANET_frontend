package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ImageRepository {

    suspend fun getImageUrl(file: MultipartBody.Part): Flow<ApiState>
}