package com.example.planet.domain.usecase.ranking.season

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMySeasonRankUseCase @Inject constructor(private val rankRepository: RankRepository) {
    suspend operator fun invoke(userId: Long): Flow<ApiState> {
        return rankRepository.getMySeasonRanking(userId.toInt())
    }
}