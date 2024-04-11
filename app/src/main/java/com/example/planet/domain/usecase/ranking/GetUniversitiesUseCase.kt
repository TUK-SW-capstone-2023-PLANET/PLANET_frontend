package com.example.planet.domain.usecase.ranking

import androidx.paging.PagingData
import com.example.planet.data.ApiState
import com.example.planet.data.remote.dto.ranking.University
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUniversitiesUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    suspend operator fun invoke(): Flow<PagingData<University>> {
        return rankingRepository.getAllUniversity()

    }

    suspend fun getHigherUniversity(): Flow<ApiState> {
        return rankingRepository.getHigherUniversities()
    }

}