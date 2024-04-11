package com.example.planet.usecase.ranking.user

import com.example.planet.data.ApiState
import com.example.planet.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyUniversityRankingUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return rankingRepository.getUniversityMyRanking()
    }
}