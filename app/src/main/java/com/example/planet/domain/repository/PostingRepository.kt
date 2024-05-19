package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.CommentRequest
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.data.remote.dto.request.post.PostingInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface PostingRepository {
    suspend fun postPostingSave(postingInfo: PostingInfo): Flow<ApiState>

    suspend fun getPostedInfo(postId: Long, userId: Long): Flow<ApiState>
    suspend fun postBoardHeartSave(postId: PostId): Flow<ApiState>
    suspend fun deleteBoardHeartSave(postId: PostId): Flow<ApiState>
    suspend fun postCommentSave(comment: CommentRequest): Flow<ApiState>
    suspend fun getCommentRead(postId: Long, userId: Long): Flow<ApiState>
    
}