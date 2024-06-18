package com.example.planet.data.repository

import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.StatisticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StatisticsRepositoryImpl @Inject constructor(private val mainApi: MainApi): StatisticsRepository {
    override suspend fun readMonthPloggingLog(userId: Long, year: Int, month: Int) = flow {
        kotlin.runCatching {
            mainApi.getMonthStatistics(userId = userId, year = year, month = month)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun readWeekPloggingLog(userId: Long) = flow {
        kotlin.runCatching {
            mainApi.getWeekStatistics(userId = userId)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}