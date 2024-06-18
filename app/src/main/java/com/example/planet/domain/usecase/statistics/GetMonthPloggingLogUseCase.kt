package com.example.planet.domain.usecase.statistics

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.StatisticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonthPloggingLogUseCase @Inject constructor(private val statisticsRepository: StatisticsRepository) {
    suspend operator fun invoke(userId: Long, year: Int, month: Int): Flow<ApiState> {
        return statisticsRepository.readMonthPloggingLog(userId, year, month)
    }
}