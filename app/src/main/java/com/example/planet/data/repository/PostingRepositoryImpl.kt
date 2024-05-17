package com.example.planet.data.repository

import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.post.PostId
import com.example.planet.data.remote.dto.request.post.PostingInfo
import com.example.planet.domain.repository.PostingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostingRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
): PostingRepository {
    override suspend fun postPostingStore(postingInfo: PostingInfo): Flow<ApiState> = flow {
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
}