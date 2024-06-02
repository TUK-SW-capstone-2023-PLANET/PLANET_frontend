package com.example.planet.data.repository

import com.example.planet.data.remote.api.ai.AiApi
import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.plogging.TrashImageUrlInfo
import com.example.planet.data.remote.dto.request.plogging.PloggingInfo
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class PloggingRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    private val aiApi: AiApi
) : PloggingRepository {
    override suspend fun getAllTrashCanLocation(): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getAllTrashCanLocation()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPloggingId(userId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getPloggingId(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postTrashImages(file: MultipartBody.Part): Flow<ApiState> = flow {
        kotlin.runCatching {
            aiApi.postImage(file = file)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun postPloggingLive(ploggingData: TrashImageUrlInfo): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.postPloggingLive(trashImageUrlInfo = ploggingData)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun postPlogging(ploggingInfo: PloggingInfo): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.postPlogging(ploggingInfo = ploggingInfo)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPloggingInfo(ploggingId: Long): Flow<ApiState> = flow {
        kotlin.runCatching {
            mainApi.getPloggingInfo(ploggingId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getPloggingActiveList(
        userId: Long,
        year: Int,
        month: Int
    ) = flow {
        kotlin.runCatching {
            mainApi.getPloggingActiveList(userId, year, month)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}