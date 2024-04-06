package com.example.planet.usecase.ranking

import com.example.planet.data.ApiState
import com.example.planet.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanetUserUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return rankingRepository.getAllSeasonUserInfo()
    }

    suspend fun top3PlanetUser(): Flow<ApiState> {
        return rankingRepository.getTop3PlanetUser()
    }
}