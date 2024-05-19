package com.example.planet.data.repository

import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.CommentId
import com.example.planet.data.remote.dto.request.post.CommentRequest
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.data.remote.dto.request.post.PostingInfo
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Query
import javax.inject.Inject

class PostingRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
): PostingRepository {
    override suspend fun postPostingSave(postingInfo: PostingInfo): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.postPosting(postingInfo)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPostedInfo(postId: Long, userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getPosted(postId = postId, userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postBoardHeartSave(postId: PostId): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.postBoardHeartSave(postId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deletePostedHeartSave(postId: PostId): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.deletePostedHeartSave(postId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postCommentSave(comment: CommentRequest): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.postComment(comment)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCommentRead(postId: Long, userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getComment(postId = postId, userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteComment(commentId: CommentId): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.deleteComment(commentId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPopularPostedList(): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getPopularPosted()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllPosted(type: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getAllPosted(type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getViewPosted(type: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getViewPosted(type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getHotPosted(type: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getHotPosted(type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}
