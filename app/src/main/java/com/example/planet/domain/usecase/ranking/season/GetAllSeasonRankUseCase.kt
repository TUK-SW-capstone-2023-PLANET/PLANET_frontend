package com.example.planet.domain.usecase.ranking.season

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSeasonRankUseCase @Inject constructor(private val rankRepository: RankRepository) {
    operator fun invoke(): Flow<PagingData<SeasonUser>> {
        return rankRepository.getAllSeasonUser()
    }
}