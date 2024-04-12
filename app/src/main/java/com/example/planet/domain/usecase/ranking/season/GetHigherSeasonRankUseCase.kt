package com.example.planet.domain.usecase.ranking.season

import androidx.paging.PagingData
import com.example.planet.data.ApiState
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHigherSeasonRankUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return rankingRepository.getSeasonTop5UserInfo()
    }
}