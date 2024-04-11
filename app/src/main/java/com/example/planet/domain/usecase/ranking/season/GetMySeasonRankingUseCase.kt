package com.example.planet.domain.usecase.ranking.season

import com.example.planet.data.ApiState
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMySeasonRankingUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return rankingRepository.getMySeasonRanking()
    }
}