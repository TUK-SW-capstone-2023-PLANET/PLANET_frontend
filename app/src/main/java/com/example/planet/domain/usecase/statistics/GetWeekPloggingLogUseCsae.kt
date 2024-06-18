package com.example.planet.domain.usecase.statistics

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.StatisticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeekPloggingLogUseCsae @Inject constructor(private val statisticsRepository: StatisticsRepository) {
    suspend operator fun invoke(userId: Long): Flow<ApiState> {
        return statisticsRepository.readWeekPloggingLog(userId)
    }
}