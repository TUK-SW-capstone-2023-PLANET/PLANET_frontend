package com.example.planet.data.repository

import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val mainApi: MainApi): SearchRepository {
    override suspend fun searchPlanet(search: String) = flow {
        kotlin.runCatching {
            mainApi.searchPlanetRank(search)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchUniversity(search: String) = flow {
        kotlin.runCatching {
            mainApi.searchUniversityRank(search)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchUniversityUser(search: String, userId: Long) = flow {
        kotlin.runCatching {
            mainApi.searchUniversityUserRank(search = search, userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchSeason(search: String) = flow {
        kotlin.runCatching {
            mainApi.searchSeasonRank(search)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchRecentlyWord(userId: Long) = flow {
        kotlin.runCatching {
            mainApi.getRecentlySearch(userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchPosted(type: String, userId: Long, search: String) = flow {
        kotlin.runCatching {
            mainApi.searchPosted(type=type, userId = userId, search = search)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}