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
    override suspend fun postPostingSave(postingInfo: PostingInfo, type: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.postPosting(postingInfo, type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readPostedInfo(postId: Long, userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getPosted(postId = postId, userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deletePosted(postId: PostId): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.deletePosted(postId)
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

    override suspend fun readPopularPostedList(): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getPopularPosted()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readAllPosted(type: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.readAllPosted(type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readViewPosted(type: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.readViewPosted(type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readHotPosted(type: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.readHotPosted(type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postCommentHeart(commentId: CommentId) = flow {
        kotlin.runCatching {
            mainApi.postCommentHeart(commentId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteCommentHeart(commentId: CommentId) = flow {
        kotlin.runCatching {
            mainApi.deleteCommentHeart(commentId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readAllMyPosted(userId: Long, type: String) = flow {
        kotlin.runCatching {
            mainApi.readAllMyPosted(userId = userId, type = type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readAllMyComment(userId: Long, type: String) = flow {
        kotlin.runCatching {
            mainApi.readAllMyComment(userId = userId, type = type)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}
