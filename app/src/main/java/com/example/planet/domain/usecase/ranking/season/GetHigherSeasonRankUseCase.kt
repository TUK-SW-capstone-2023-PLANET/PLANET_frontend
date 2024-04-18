package com.example.planet.domain.usecase.ranking.season

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHigherSeasonRankUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(userId: String): Flow<ApiState> {
        return rankingRepository.getSeasonTop5UserInfo(userId.toInt())
    }
}