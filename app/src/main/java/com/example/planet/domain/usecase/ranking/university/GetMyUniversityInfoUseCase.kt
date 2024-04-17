package com.example.planet.domain.usecase.ranking.university

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyUniversityInfoUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    operator fun invoke(userId: Int = 0): Flow<ApiState> {
        return rankingRepository.getMyUniversityInfo(userId)
    }
}