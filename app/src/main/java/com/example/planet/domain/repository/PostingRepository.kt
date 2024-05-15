package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.PostingInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface PostingRepository {
    suspend fun postPostingStore(postingInfo: PostingInfo): Flow<ApiState>

    suspend fun getPostedInfo(postId: Long, userId: Long): Flow<ApiState>


}