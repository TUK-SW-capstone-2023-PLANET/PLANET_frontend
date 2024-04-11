package com.example.planet.domain.usecase.ranking

import com.example.planet.data.ApiState
import com.example.planet.data.repository.RankingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUniversityUserInfoUseCase @Inject constructor(private val rankingRepository: RankingRepository) {
    // 자대 대학교 개인 유저 랭킹 조회
    suspend operator fun invoke(): Flow<ApiState> {
        return rankingRepository.getAllUniversityUserInfo()
    }
}