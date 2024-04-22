package com.example.planet.domain.usecase.ranking.season

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHigherSeasonRankUseCase @Inject constructor(private val rankRepository: RankRepository) {
    suspend operator fun invoke(userId: String): Flow<ApiState> {
        return rankRepository.getSeasonTop5UserInfo(userId.toInt())
    }
}