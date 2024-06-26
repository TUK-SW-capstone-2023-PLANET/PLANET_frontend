package com.example.planet.domain.usecase.ranking.universityuser

import androidx.paging.PagingData
import com.example.planet.data.remote.dto.response.ranking.universityuser.UniversityUser
import com.example.planet.domain.repository.RankRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUniversityUserRankUseCase @Inject constructor(private val rankRepository: RankRepository) {
    // 자대 대학교 개인 유저 랭킹 조회
    operator fun invoke(): Flow<PagingData<UniversityUser>> {
        return rankRepository.getAllUniversityUser()
    }
}