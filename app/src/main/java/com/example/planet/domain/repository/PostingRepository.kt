package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.PostingInfo
import kotlinx.coroutines.flow.Flow

interface PostingRepository {
    suspend fun postPostingStore(postingInfo: PostingInfo): Flow<ApiState>
}