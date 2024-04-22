package com.example.planet.domain.usecase.ranking.planet

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlanetUserRankUseCase @Inject constructor(private val rankRepository: RankRepository) {
    operator fun invoke(): Flow<PagingData<PlanetRankingUser>> {
        return rankRepository.getAllPlanetUserRanking()
    }
}