package com.example.planet.domain.usecase.ranking.planet

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHigherPlanetUserUseCase @Inject constructor(private val rankRepository: RankRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return rankRepository.getTop3PlanetUser()
    }
}