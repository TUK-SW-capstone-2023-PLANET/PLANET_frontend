package com.example.planet.domain.usecase.ranking.planet

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlanetUserRankUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    operator fun invoke(): Flow<PagingData<PlanetRankingUser>> {
        return rankingRepository.getAllPlanetUserRanking()
    }
}