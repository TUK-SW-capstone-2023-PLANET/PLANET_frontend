package com.example.planet.domain.usecase.ranking.university

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUniversitiesUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    operator fun invoke(): Flow<PagingData<University>> {
        return rankingRepository.getAllUniversity()

    }

    suspend fun getHigherUniversity(): Flow<ApiState> {
        return rankingRepository.getHigherUniversities()
    }

}