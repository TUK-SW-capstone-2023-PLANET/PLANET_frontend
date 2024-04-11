package com.example.planet.domain.usecase.ranking

import com.example.planet.data.ApiState
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSeasonUserUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return rankingRepository.getAllSeasonUserInfo()
    }

    suspend fun getTop5SeasonUser(): Flow<ApiState> {
        return rankingRepository.getSeasonTop5UserInfo()
    }
}