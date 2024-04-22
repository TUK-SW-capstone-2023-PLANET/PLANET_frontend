package com.example.planet.domain.usecase.ranking.university

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.response.ranking.university.University
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUniversitiesUseCase @Inject constructor(private val rankRepository: RankRepository) {
    operator fun invoke(): Flow<PagingData<University>> {
        return rankRepository.getAllUniversity()

    }
}