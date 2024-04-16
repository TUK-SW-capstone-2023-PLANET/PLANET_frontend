package com.example.planet.domain.usecase.ranking.season

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSeasonRankUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    operator fun invoke(): Flow<PagingData<SeasonUser>> {
        return rankingRepository.getAllSeasonUser()
    }
}