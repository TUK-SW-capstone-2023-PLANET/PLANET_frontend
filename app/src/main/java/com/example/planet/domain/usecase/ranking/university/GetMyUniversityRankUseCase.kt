package com.example.planet.domain.usecase.ranking.university

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyUniversityRankUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(userId: String): Flow<ApiState> {
        return rankingRepository.getUniversityMyRanking(userId.toInt())
    }
}