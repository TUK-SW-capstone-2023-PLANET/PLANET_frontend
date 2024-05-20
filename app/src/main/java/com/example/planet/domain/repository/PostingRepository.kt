package com.example.planet.domain.repository

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.CommentId
import com.example.planet.data.remote.dto.request.post.CommentRequest
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.data.remote.dto.request.post.PostingInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface PostingRepository {
    suspend fun postPostingSave(postingInfo: PostingInfo): Flow<ApiState>

    suspend fun readPostedInfo(postId: Long, userId: Long): Flow<ApiState>
    suspend fun deletePosted(postId: PostId): Flow<ApiState>
    suspend fun postBoardHeartSave(postId: PostId): Flow<ApiState>
    suspend fun deletePostedHeartSave(postId: PostId): Flow<ApiState>
    suspend fun postCommentSave(comment: CommentRequest): Flow<ApiState>
    suspend fun getCommentRead(postId: Long, userId: Long): Flow<ApiState>
    suspend fun deleteComment(commentId: CommentId): Flow<ApiState>
    suspend fun readPopularPostedList(): Flow<ApiState>
    suspend fun readAllPosted(type: String): Flow<ApiState>
    suspend fun readViewPosted(type: String): Flow<ApiState>
    suspend fun readHotPosted(type: String): Flow<ApiState>
    suspend fun postCommentHeart(commentId: CommentId): Flow<ApiState>
    suspend fun deleteCommentHeart(commentId: CommentId): Flow<ApiState>
    suspend fun readAllMyPosted(userId: Long, type: String): Flow<ApiState>
    suspend fun readAllMyComment(userId: Long, type: String): Flow<ApiState>





}